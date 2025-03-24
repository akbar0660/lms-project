package az.edu.turing.msauth.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String username;
}
