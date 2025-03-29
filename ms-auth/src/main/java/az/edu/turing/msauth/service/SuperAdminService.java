package az.edu.turing.msauth.service;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.mapper.superadmin.SuperAdminMapper;
import az.edu.turing.msauth.model.request.SuperAdminRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.model.response.SuperAdminResponse;
import az.edu.turing.msauth.repository.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class SuperAdminService {

    private final JwtService jwtService;

    private final SuperAdminRepository superAdminRepository;

    private final SuperAdminMapper superAdminMapper = SuperAdminMapper.INSTANCE;

    public ResponseEntity<AuthResponse> updateAdmin(String username, SuperAdminRequest request) {
        SuperAdmin admin = superAdminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("no user like this"));
        admin.setEmail(request.getEmail());
        admin.setPhone(request.getPhone());
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setProfileCompleted(true);

        superAdminRepository.save(admin);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        var jwtToken = jwtService.generateAccessToken(admin);
        var refreshToken = jwtService.generateRefreshToken(admin);

        return ResponseEntity.ok()
                .header("Location", location.toString())
                .body(AuthResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .requiresProfileCompletion(false)
                        .build());
    }
}
