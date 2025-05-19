package az.edu.turing.msauth.controller;


import az.edu.turing.msauth.messaging.AuthEventProducer;
import az.edu.turing.msauth.messaging.StudentCreatedEvent;
import az.edu.turing.msauth.model.request.StudentProfileCreateRequest;
import az.edu.turing.msauth.model.request.StudentRequest;
import az.edu.turing.msauth.model.response.StudentResponse;
import az.edu.turing.msauth.repository.StudentRepository;
import az.edu.turing.msauth.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasRole('STAFF,SUPER_ADMIN')")
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request) {
        StudentResponse student = studentService.createStudent(request);
        return ResponseEntity.ok(student);
    }
}
