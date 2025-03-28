package az.edu.turing.msauth.controller;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.model.request.AuthRequest;
import az.edu.turing.msauth.model.request.RefreshRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.model.response.RefreshResponse;
import az.edu.turing.msauth.repository.UserRepository;
import az.edu.turing.msauth.service.AuthService;
import az.edu.turing.msauth.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {

        return authService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        return authService.logout(authHeader);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestBody RefreshRequest request) {
        return authService.refreshAccessToken(request);
    }

}
