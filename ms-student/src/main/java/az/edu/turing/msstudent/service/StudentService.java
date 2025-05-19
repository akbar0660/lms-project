package az.edu.turing.msstudent.service;

import az.edu.turing.msstudent.model.FileResponseDto;
import az.edu.turing.msstudent.model.StudentCreatedEvent;
import az.edu.turing.msstudent.model.StudentDto;
import az.edu.turing.msstudent.model.UpdateProfileRequest;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {
    void createProfileFromEvent(StudentCreatedEvent event);

    StudentDto getStudentProfile(Long studentId);

    StudentDto updateProfile(Long studentId, UpdateProfileRequest request);

    FileResponseDto uploadCV(Long studentId, MultipartFile file);

    FileResponseDto uploadProfilePhoto(Long studentId, MultipartFile image);


}
