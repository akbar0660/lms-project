package az.edu.turing.msauth.model.request;

import az.edu.turing.msauth.model.enums.Class;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String phone;

    @NotNull
    private Class classType;

    @NotNull
    private List<UniversityRequest> universityRequest;

    @NotNull
    private Double totalPayment;

    @NotNull
    private Integer countOfMonths;

}
