package az.edu.turing.msauth.entity;

import az.edu.turing.msauth.model.enums.Class;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends UserEntity {

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Class classOfStudent;

    private Double totalPayment;

    private Integer countOfMonths;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_university",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "university_id")
    )
    private List<University> universities;

}
