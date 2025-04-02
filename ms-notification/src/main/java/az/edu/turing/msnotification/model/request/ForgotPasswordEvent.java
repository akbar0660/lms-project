package az.edu.turing.msnotification.model.request;

import java.time.LocalDateTime;

public record ForgotPasswordEvent(
        String email,
        String token,
        LocalDateTime expirationTime
){}
