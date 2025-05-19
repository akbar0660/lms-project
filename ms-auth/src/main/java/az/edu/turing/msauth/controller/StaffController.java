package az.edu.turing.msauth.controller;


import az.edu.turing.msauth.model.request.StaffRequest;
import az.edu.turing.msauth.model.response.StaffResponse;
import az.edu.turing.msauth.service.StaffService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService service;
    private final StaffService staffService;

    @PostMapping()
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<StaffResponse> create(@Valid @RequestBody StaffRequest request){

        return service.createStaff(request);
    }

    @GetMapping("/emails")
    @PermitAll
    public List<String> getAllStaffEmails() {
        return staffService.getAllStaffEmails();
    }
}
