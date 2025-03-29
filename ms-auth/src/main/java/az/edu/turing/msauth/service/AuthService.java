package az.edu.turing.msauth.service;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.entity.UserEntity;
import az.edu.turing.msauth.exception.InvalidTokenException;
import az.edu.turing.msauth.model.request.AuthRequest;
import az.edu.turing.msauth.model.request.RefreshRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.model.response.RefreshResponse;
import az.edu.turing.msauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisTemplate<String, Object> redisTemplate;

    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean requiresCompletion = user instanceof SuperAdmin &&
                !((SuperAdmin) user).isProfileCompleted();

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
}
