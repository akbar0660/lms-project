package az.edu.turing.msstudent.repo;

import az.edu.turing.msstudent.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentProfile, Long> {
    List<StudentProfile> findAllByGroupId(Long groupId);
    Optional<StudentProfile> findByUsername(String username);

}
