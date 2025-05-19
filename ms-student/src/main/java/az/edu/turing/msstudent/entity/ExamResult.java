package az.edu.turing.msstudent.entity;

import az.edu.turing.msstudent.model.ExamType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private String examName;
    private String pdfUrl;
    private Double maxScore;
    private Double studentScore;
    private LocalDate examDate;
}
