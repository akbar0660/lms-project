package az.edu.turing.msnotification.repository;

import az.edu.turing.msnotification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByEmailOrderByCreatedAtDesc(String email);

    List<Notification> findByUsernameOrderByCreatedAtDesc(String username);
}