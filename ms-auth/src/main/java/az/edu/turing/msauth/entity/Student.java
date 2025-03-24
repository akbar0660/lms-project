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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<University> universities;

}
