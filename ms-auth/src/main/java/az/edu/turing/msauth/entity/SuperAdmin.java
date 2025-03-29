package az.edu.turing.msauth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class SuperAdmin extends UserEntity {

    @Column(nullable = false)
    private boolean isProfileCompleted = false;

}
