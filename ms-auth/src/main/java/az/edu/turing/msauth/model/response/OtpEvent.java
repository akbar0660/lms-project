package az.edu.turing.msauth.model.response;

public record OtpEvent(
        String email,
        String otp
) {}