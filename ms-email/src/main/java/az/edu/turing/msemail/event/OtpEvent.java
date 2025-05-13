package az.edu.turing.msemail.event;

public record OtpEvent(
        String email,
        String otp
) {}