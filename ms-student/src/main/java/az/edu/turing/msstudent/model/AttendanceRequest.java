package az.edu.turing.msstudent.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AttendanceRequest {
    private Long groupId;
    private LocalDate lessonDate;
    private List<Long> presentStudentIds;
}

