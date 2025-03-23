package az.edu.turing.msauth.entity;

import az.edu.turing.msauth.model.enums.Class;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("STUDENT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends UserEntity {

    private LocalDate dateOfBirth;

    private Class classOfStudent;

    private double totalPayment;

    private int countOfMonths;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<University> universities;

}
