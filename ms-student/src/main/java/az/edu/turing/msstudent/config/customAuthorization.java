package az.edu.turing.msstudent.config;

import az.edu.turing.msstudent.entity.StudentProfile;
import az.edu.turing.msstudent.repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("customAuthorization")
@RequiredArgsConstructor
public class customAuthorization {
    private final StudentRepository studentRepository;

    public boolean isMentor() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return studentRepository.findByUsername(userId)
                .map(StudentProfile::isMentor)
                .orElse(false);
    }

    public boolean isClassRep() {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userId);
        return studentRepository.findByUsername(userId)
                .map(StudentProfile::isClassRep)
                .orElse(false);
    }

}
