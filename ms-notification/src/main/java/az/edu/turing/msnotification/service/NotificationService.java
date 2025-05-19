package az.edu.turing.msnotification.service;

import az.edu.turing.msnotification.client.StaffClient;
import az.edu.turing.msnotification.component.NotificationEventProducer;
import az.edu.turing.msnotification.component.NotificationListener;
import az.edu.turing.msnotification.entity.Notification;
import az.edu.turing.msnotification.model.enums.NotificationType;
import az.edu.turing.msnotification.model.request.AttendanceChangedMessage;
import az.edu.turing.msnotification.model.request.EmailPayload;
import az.edu.turing.msnotification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repository;
    private final NotificationEventProducer notificationEventProducer;
    private final StaffClient staffClient;


    public List<Notification> getUserNotificationsViaMail(String email) {
        return repository.findByEmailOrderByCreatedAtDesc(email);
    }

    public List<Notification> getUserNotificationsViaUsername(String username) {
        return repository.findByUsernameOrderByCreatedAtDesc(username);
    }

    public void saveNotification(String email, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setMessage(message);
        notification.setType(type);
        repository.save(notification);
    }

    public void saveNotification(String name,String username, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setUsername(username);
        notification.setName(name);
        notification.setMessage(message);
        notification.setType(type);
        repository.save(notification);
    }
    public void notifyAllStaffAboutAttendance(AttendanceChangedMessage message) {
        List<String> staffEmails = staffClient.getAllStaffEmails();

        for (String staffEmail : staffEmails) {
            EmailPayload emailPayload = buildEmailPayload(message, staffEmail);
            notificationEventProducer.sendEmailNotification(emailPayload);
            ;

            saveNotification(
                    message.getName(),
                    message.getUsername(),
                    message.getMessage() + " for group " + message.getGroupId() + " on " + message.getLessonDate(),
                    NotificationType.ATTENDANCE_CHANGED
            );
        }
    }

    private EmailPayload buildEmailPayload(AttendanceChangedMessage message, String staffEmail) {
        return new EmailPayload(
                staffEmail,
                "Attendance Changed Notification",
                "Attendance has been updated for group " + message.getGroupId() + " on " + message.getLessonDate() +
                        "\nStudent: " + message.getName() +
                        "\nUsername: " + message.getUsername()
        );
    }

}