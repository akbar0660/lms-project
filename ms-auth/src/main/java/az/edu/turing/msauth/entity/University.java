package az.edu.turing.msauth.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String universityName;
    private String major;
    private int courseYear;
    private String educationLevel;

    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

}
