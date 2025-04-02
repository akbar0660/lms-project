package az.edu.turing.msauth.controller;

import az.edu.turing.msauth.model.request.AuthRequest;
import az.edu.turing.msauth.model.request.RefreshRequest;
import az.edu.turing.msauth.model.request.ResetPasswordRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.model.response.RefreshResponse;
import az.edu.turing.msauth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPasswordWithOtp(
                request.email(),
                request.otp(),
                request.newPassword()
        );
        return ResponseEntity.ok("Parol uğurla yeniləndi");
    }

    @PostMapping ("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody String email){
        authService.forgotPassword(email);
        return ResponseEntity.ok("Forgot password");
    }


}
