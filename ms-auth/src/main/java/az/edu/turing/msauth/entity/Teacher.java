package az.edu.turing.msauth.entity;

import az.edu.turing.msauth.model.enums.Class;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends UserEntity {

    private String specialization;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Class classOfTeacher;

}
