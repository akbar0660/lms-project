package az.edu.turing.msauth.util;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.entity.UserEntity;
import az.edu.turing.msauth.model.enums.UserRole;
import az.edu.turing.msauth.repository.SuperAdminRepository;
import az.edu.turing.msauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final SuperAdminRepository superAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (superAdminRepository.count() == 0) {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setUsername("TuringSuperAdmin");
            superAdmin.setPassword(passwordEncoder.encode("TuringSuperAdmin"));
            superAdmin.setRole(UserRole.SUPER_ADMIN);
            superAdmin.setProfileCompleted(false);

            superAdminRepository.save(superAdmin);
        }
    }
}

