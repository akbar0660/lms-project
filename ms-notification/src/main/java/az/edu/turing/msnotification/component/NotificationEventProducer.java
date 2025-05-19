package az.edu.turing.msnotification.component;

import az.edu.turing.msnotification.config.RabbitMQConfig;
import az.edu.turing.msnotification.model.request.AttendanceChangedMessage;
import az.edu.turing.msnotification.model.request.EmailPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendEmailNotification(EmailPayload emailPayload) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_EXCHANGE,
                RabbitMQConfig.STAFF_ATTENDANCE_NOTIFICATION_ROUTING_KEY,
                emailPayload
        );
    }
}

