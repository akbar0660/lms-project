package az.edu.turing.msauth.model.request;

public record ResetPasswordRequest(
        String email,
        String otp,
        String newPassword
) {}