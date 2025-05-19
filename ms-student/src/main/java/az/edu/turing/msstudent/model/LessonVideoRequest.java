package az.edu.turing.msstudent.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class LessonVideoRequest {
    private Long groupId;
    private LocalDate lessonDate;
    private MultipartFile video;
}

