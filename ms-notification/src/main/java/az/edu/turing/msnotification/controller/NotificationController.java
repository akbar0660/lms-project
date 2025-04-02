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

    @GetMapping
    public List<Notification> getUserNotifications(
            @RequestParam String email
    ) {
        return notificationService.getUserNotifications(email);
    }
}
