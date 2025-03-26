package az.edu.turing.msauth.controller;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.model.request.AuthRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.repository.UserRepository;
import az.edu.turing.msauth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean requiresCompletion = user instanceof SuperAdmin &&
                !((SuperAdmin) user).isProfileCompleted();

        var jwtToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .requiresProfileCompletion(requiresCompletion)
                .role(user.getRole())
                .build();
    }

}
