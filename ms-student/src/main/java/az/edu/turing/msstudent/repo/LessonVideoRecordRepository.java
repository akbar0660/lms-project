package az.edu.turing.msstudent.repo;

import az.edu.turing.msstudent.entity.LessonVideoRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LessonVideoRecordRepository extends JpaRepository<LessonVideoRecord, Long> {
    Optional<LessonVideoRecord> findByGroupIdAndLessonDate(Long groupId, LocalDate lessonDate);
}
