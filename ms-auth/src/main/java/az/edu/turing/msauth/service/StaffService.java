package az.edu.turing.msauth.service;

import az.edu.turing.msauth.entity.Staff;
import az.edu.turing.msauth.mapper.staff.StaffMapper;
import az.edu.turing.msauth.model.enums.UserRole;
import az.edu.turing.msauth.model.request.StaffRequest;
import az.edu.turing.msauth.model.response.StaffResponse;
import az.edu.turing.msauth.repository.StaffRepository;
import az.edu.turing.msauth.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class StaffService {

    private static int count = 1;

    private final StaffRepository staffRepository;

    private final PasswordEncoder passwordEncoder;

    private final StaffMapper staffMapper = StaffMapper.INSTANCE;

    public ResponseEntity<StaffResponse> createStaff(StaffRequest request) {

        String userName = "STAFF#".concat(String.valueOf(count++));
        String password = PasswordGenerator.generatePassword(8);

        Staff staff = staffMapper.requestToEntity(request);

        staff.setUsername(userName);
        staff.setPassword(passwordEncoder.encode(password));
        staff.setRole(UserRole.STAFF);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).body(staffMapper.entityToResponse(staffRepository.save(staff)));
    }
}
