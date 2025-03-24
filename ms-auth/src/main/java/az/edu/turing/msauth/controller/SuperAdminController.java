package az.edu.turing.msauth.controller;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.repository.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminRepository superAdminRepository;

    @PostMapping()
    public void createSuperAdmin() {
        SuperAdmin superAdmin = new SuperAdmin();
        superAdmin.setUsername("admin");
        superAdmin.setPassword("admin");
        superAdminRepository.save(superAdmin);
    }
}
