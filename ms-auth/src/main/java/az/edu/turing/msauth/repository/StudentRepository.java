package az.edu.turing.msauth.repository;

import az.edu.turing.msauth.entity.Student;
import az.edu.turing.msauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
