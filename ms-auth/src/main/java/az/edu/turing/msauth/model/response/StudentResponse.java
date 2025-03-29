package az.edu.turing.msauth.model.response;

import az.edu.turing.msauth.entity.University;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {

    private String firstName;
    private String lastName;
    private List<String> universityNames;
}
