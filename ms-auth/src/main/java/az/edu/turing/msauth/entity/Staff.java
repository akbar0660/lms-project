package az.edu.turing.msauth.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STAFF")
public class Staff extends UserEntity {

}
