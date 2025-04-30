package az.edu.turing.msauth.service;

import az.edu.turing.msauth.entity.OtpCode;
import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.entity.UserEntity;
import az.edu.turing.msauth.exception.InvalidTokenException;
import az.edu.turing.msauth.messaging.AuthEventProducer;
import az.edu.turing.msauth.model.request.AuthRequest;
import az.edu.turing.msauth.model.request.RefreshRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.model.response.RefreshResponse;
import az.edu.turing.msauth.repository.OtpRepository;
import az.edu.turing.msauth.repository.StudentRepository;
import az.edu.turing.msauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthEventProducer eventProducer;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean requiresCompletion = user instanceof SuperAdmin &&
                !user.isProfileCompleted();

        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        redisTemplate.opsForValue().set(
                "user:" + user.getId(),
                refreshToken,
                Duration.ofDays(7)
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri();

        return ResponseEntity.ok().header("Location", location.toString()).body(AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .requiresProfileCompletion(requiresCompletion)
                .role(user.getRole())
                .build());
    }

    public ResponseEntity<String> logout(String authHeader) {
        String token = authHeader.substring(7);
        String userName = jwtService.extractUsername(token);

        redisTemplate.delete("user:" + userName);
        return ResponseEntity.ok().body("Successfully logged out");
    }


    public ResponseEntity<RefreshResponse> refreshAccessToken(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        String userName = jwtService.extractUsername(refreshToken);

        String storedRefreshToken = Objects.requireNonNull(redisTemplate.opsForValue().get("user:" + userName)).toString();
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new InvalidTokenException("Invalid refresh token");
        }

        UserEntity user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String newAccessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok().body(new RefreshResponse(newAccessToken, refreshToken));
    }


    public void forgotPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String otpCode = String.format("%06d", new Random().nextInt(999999));

        OtpCode otp = new OtpCode();
        otp.setCode(otpCode);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otp.setUser(user);
        otpRepository.save(otp);

        try {
            eventProducer.sendOtpEvent(email, otpCode);
        } catch (AmqpException e) {
            AuthService.log.error("OTP göndərilmədi: {}", email, e);
            throw new RuntimeException("OTP göndərilmədi");
        }
    }
    public void resetPasswordWithOtp(String email, String otpCode, String newPassword) {
        OtpCode otp = otpRepository.findByCodeAndUserEmail(otpCode, email)
                .orElseThrow(() -> new RuntimeException("Yanlış OTP"));

        if (otp.isUsed()) {
            throw new RuntimeException("OTP artıq istifadə edilib");
        }

        if (LocalDateTime.now().isAfter(otp.getExpiryTime())) {
            throw new RuntimeException("OTP-nin müddəti bitib");
        }

        UserEntity user = otp.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otp.setUsed(true);
        otpRepository.save(otp);

        try {
            eventProducer.sendPasswordResetNotification(email);
        } catch (AmqpException e) {
            log.error("Bildiriş göndərilmədi: {}", email, e);
        }
    }
}
