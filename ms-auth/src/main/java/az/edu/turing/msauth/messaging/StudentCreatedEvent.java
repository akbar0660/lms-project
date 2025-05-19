package az.edu.turing.msauth.messaging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCreatedEvent {
    private Long studentId;
    private String name;
    private String username;
}