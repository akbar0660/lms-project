package az.edu.turing.msnotification.component;

import az.edu.turing.msnotification.service.NotificationService;
import az.edu.turing.msnotification.model.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final NotificationService notificationService;

    @RabbitListener(queues = "password-reset-notification-queue")
    public void handlePasswordReset(String email) {
        notificationService.saveNotification(
                email,
                "PASSWORD UPDATED",
                NotificationType.PASSWORD_RESET
        );
        log.info("Parol yeniləmə bildirişi qeyd edildi: {}", email);
    }
}