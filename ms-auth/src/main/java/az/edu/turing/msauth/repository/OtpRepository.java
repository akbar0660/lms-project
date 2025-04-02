package az.edu.turing.msauth.repository;

import az.edu.turing.msauth.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByCodeAndUserEmail(String code, String email);
    List<OtpCode> findByUserEmailAndUsedFalse(String email);
}