package az.edu.turing.msnotification.entity;

import az.edu.turing.msnotification.model.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "notifications")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String message;
    private String name;
    private String username;



    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime createdAt = LocalDateTime.now();
}