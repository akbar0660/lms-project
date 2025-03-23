package az.edu.turing.msauth.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SUPER_ADMIN")
public class SuperAdmin extends UserEntity {

}
