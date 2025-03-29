package az.edu.turing.msauth.service;

import az.edu.turing.msauth.entity.Student;
import az.edu.turing.msauth.entity.University;
import az.edu.turing.msauth.mapper.University.UniversityMapper;
import az.edu.turing.msauth.mapper.student.StudentMapper;
import az.edu.turing.msauth.model.enums.UserRole;
import az.edu.turing.msauth.model.request.StudentRequest;
import az.edu.turing.msauth.model.response.StudentResponse;
import az.edu.turing.msauth.repository.StudentRepository;
import az.edu.turing.msauth.repository.UniversityRepository;
import az.edu.turing.msauth.util.PasswordGenerator;
import az.edu.turing.msauth.util.StudentUserNameGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper = StudentMapper.INSTANCE;
    private final UniversityMapper universityMapper = UniversityMapper.INSTANCE;

    @Transactional
    public ResponseEntity<StudentResponse> createStudent(StudentRequest request) {

        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setClassOfStudent(request.getClassType());
        student.setCountOfMonths(request.getCountOfMonths());
        student.setTotalPayment(request.getTotalPayment());
        student.setUsername(StudentUserNameGenerator.generateUserName(request));
        student.setPassword(PasswordGenerator.generatePassword(8));
        student.setRole(UserRole.STUDENT);
        student.setPhone(request.getPhone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        student.setDateOfBirth(LocalDate.parse(request.getDateOfBirth(), formatter));

        List<University> universities = request.getUniversityRequest().stream()
                .map(universityMapper::toEntity)
                .toList();

        student.setUniversities(universities);
        Student savedStudent = studentRepository.save(student);
        List<String> universityNames = new ArrayList<>();
        savedStudent.getUniversities().forEach(university -> universityNames.add(university.getUniversityName()));
        return ResponseEntity.ok().body(StudentResponse.builder()
                .firstName(savedStudent.getFirstName())
                .lastName(savedStudent.getLastName())
                .universityNames(universityNames).build());

    }
}
