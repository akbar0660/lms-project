package az.edu.turing.msauth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OtpCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private LocalDateTime expiryTime;
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
