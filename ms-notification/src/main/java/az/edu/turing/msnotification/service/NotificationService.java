package az.edu.turing.msnotification.service;

import az.edu.turing.msnotification.entity.Notification;
import az.edu.turing.msnotification.model.enums.NotificationType;
import az.edu.turing.msnotification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;

    public List<Notification> getUserNotifications(String email) {
        return repository.findByEmailOrderByCreatedAtDesc(email);
    }

    public void saveNotification(String email, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setMessage(message);
        notification.setType(type);
        repository.save(notification);
    }
}