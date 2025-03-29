package az.edu.turing.msauth.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToMany(mappedBy = "universities", fetch = FetchType.LAZY)
    private List<Student> students;

}
