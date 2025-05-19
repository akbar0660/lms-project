package az.edu.turing.msnotification.controller;

import az.edu.turing.msnotification.service.NotificationService;
import az.edu.turing.msnotification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/by-email")
    public List<Notification> getUserNotificationsByEmail(
            @RequestParam String email) {
        return notificationService.getUserNotificationsViaMail(email);
    }

    @GetMapping("/by-username")
    public List<Notification> getUserNotificationsByUsername(
            @RequestParam String username) {
        return notificationService.getUserNotificationsViaUsername(username);
    }
}
