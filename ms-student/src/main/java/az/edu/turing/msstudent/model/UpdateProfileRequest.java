package az.edu.turing.msstudent.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    private String about;
    private String instUrl;
    private String linkedinUrl;
}
