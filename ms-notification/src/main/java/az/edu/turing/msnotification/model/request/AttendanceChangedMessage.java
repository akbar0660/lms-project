package az.edu.turing.msnotification.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceChangedMessage {
    private String message;
    private Long groupId;
    private LocalDate lessonDate;
    private String name;
    private String username;

}
