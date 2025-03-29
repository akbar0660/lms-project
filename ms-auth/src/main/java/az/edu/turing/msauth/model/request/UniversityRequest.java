package az.edu.turing.msauth.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversityRequest {

    @NotBlank
    private String universityName;

    @NotBlank
    private String major;

    @Min(value = 1)
    @Max(value = 7)
    private int courseYear;

    @NotBlank
    private String educationLevel;
}
