package az.edu.turing.msemail;

public record OtpEvent(
        String email,
        String otp
) {}