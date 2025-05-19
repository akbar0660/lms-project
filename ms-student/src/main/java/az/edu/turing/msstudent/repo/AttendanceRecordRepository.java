package az.edu.turing.msstudent.repo;

import az.edu.turing.msstudent.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    Optional<AttendanceRecord> findByGroupIdAndLessonDate(Long groupId, LocalDate lessonDate);
}
