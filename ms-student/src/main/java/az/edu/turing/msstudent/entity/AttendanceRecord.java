package az.edu.turing.msstudent.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long groupId;
    private LocalDate lessonDate;

    @ElementCollection
    private List<Long> presentStudentIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceRecord that = (AttendanceRecord) o;
        return Objects.equals(groupId, that.groupId)
                && Objects.equals(lessonDate, that.lessonDate)
                && Objects.equals(presentStudentIds, that.presentStudentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, lessonDate, presentStudentIds);
    }
}
