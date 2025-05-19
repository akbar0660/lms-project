package az.edu.turing.msemail.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailPayload {
    private String to;
    private String subject;
    private String body;
}
