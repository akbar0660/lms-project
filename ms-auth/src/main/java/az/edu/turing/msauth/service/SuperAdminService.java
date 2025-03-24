package az.edu.turing.msauth.service;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.model.request.SuperAdminRequest;
import az.edu.turing.msauth.model.response.SuperAdminResponse;
import az.edu.turing.msauth.repository.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final SuperAdminRepository superAdminRepository;

    public ResponseEntity<SuperAdminResponse> updateAdmin(SuperAdminRequest request) {
        if (superAdminRepository.existsByUsername(request.getUsername())) {
            SuperAdmin admin = superAdminRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("no user like this"));
            admin.setEmail(request.getEmail());
            admin.setPhone(request.getPhone());
            admin.setFirstName(request.getFirstName());
            admin.setLastName(request.getLastName());

          //  return ResponseEntity.ok().body(superAdminRepository.save(admin));
        }
        return null;
    }
}
