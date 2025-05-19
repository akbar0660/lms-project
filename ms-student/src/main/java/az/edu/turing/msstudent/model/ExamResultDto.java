package az.edu.turing.msstudent.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExamResultDto {
    private ExamType examType;
    private String examName;
    private String pdfUrl;
    private Double maxScore;
    private Double studentScore;
    private LocalDate examDate;
}
