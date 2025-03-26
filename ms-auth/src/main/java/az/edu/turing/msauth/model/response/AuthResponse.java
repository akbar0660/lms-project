package az.edu.turing.msauth.model.response;

import az.edu.turing.msauth.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private boolean requiresProfileCompletion;
    private UserRole role;
}
