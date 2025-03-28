package az.edu.turing.msauth.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshRequest {
    private String refreshToken;
}
