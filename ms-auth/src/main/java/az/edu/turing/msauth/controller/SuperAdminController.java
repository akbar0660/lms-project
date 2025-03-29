package az.edu.turing.msauth.controller;

import az.edu.turing.msauth.entity.SuperAdmin;
import az.edu.turing.msauth.model.request.SuperAdminRequest;
import az.edu.turing.msauth.model.response.AuthResponse;
import az.edu.turing.msauth.model.response.SuperAdminResponse;
import az.edu.turing.msauth.service.SuperAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminService service;

    @PutMapping("{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthResponse> completeProfile(@PathVariable String username,
                                                        @Valid @RequestBody SuperAdminRequest request) {
        return service.updateAdmin(username, request);
    }
}
