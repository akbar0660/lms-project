package az.edu.turing.msstudent.repo;

import az.edu.turing.msstudent.entity.ExamResult;
import az.edu.turing.msstudent.model.ExamType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    List<ExamResult> findByStudentId(long studentId);

    List<ExamResult> findByStudentIdAndExamType(Long studentId, ExamType examType);

    List<ExamResult> findByStudentId(Long studentId, Sort sort);

    List<ExamResult> findByStudentIdAndExamType(Long studentId, ExamType examType, Sort sort);
}

