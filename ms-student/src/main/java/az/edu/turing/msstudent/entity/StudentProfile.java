package az.edu.turing.msstudent.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfile {
    @Id
    private Long id;

    private String name;
    private String username;

    @Lob
    private String aboutMe;

    private String instagramLink;
    private String linkedinLink;
    private String profilePictureUrl;
    private Long groupId;
    private String cvUrl;
    private Double gpa;
    private boolean isMentor;
    private boolean isClassRep;


}
