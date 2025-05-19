package az.edu.turing.msstudent.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceChangedMessage {
    private String message;
    private Long groupId;
    private LocalDate lessonDate;
    private String name;
    private String username;
}