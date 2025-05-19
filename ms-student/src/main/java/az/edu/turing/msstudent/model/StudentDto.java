package az.edu.turing.msstudent.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentDto {
    private String fullName;
    private String username;
    private String aboutMe;
    private String instagramLink;
    private String linkedinLink;
    private String profilePictureUrl;
    private String cvUrl;
    private Double gpa;
    private Long groupId;
    private boolean isMentor;
    private boolean isClassRep;
}
