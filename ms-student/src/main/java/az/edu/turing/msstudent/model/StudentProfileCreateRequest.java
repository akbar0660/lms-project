package az.edu.turing.msstudent.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileCreateRequest {
    private Long id;
    private String name;
}