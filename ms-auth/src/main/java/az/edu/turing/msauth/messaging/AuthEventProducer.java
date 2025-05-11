package az.edu.turing.msauth.messaging;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendOtpEvent(String email, String otpCode) {
        try {
            rabbitTemplate.convertAndSend(
                    "auth-exchange",
                    "otp.event",
                    Map.of("email", email, "otp", otpCode)
            );
        } catch (AmqpException e) {
            log.error("OTP mesajı göndərilmədi: {}", email, e);
            throw new RuntimeException("RabbitMQ error");
        }
    }

    public void sendPasswordResetNotification(String email) {
        rabbitTemplate.convertAndSend(
                "auth-exchange",
                "password.reset.notification",
                email
        );
    }
}
