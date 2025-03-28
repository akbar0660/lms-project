package az.edu.turing.msauth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email(message = "this field must be in email format")
    private String email;

    @NotBlank
    private String phone;
}
