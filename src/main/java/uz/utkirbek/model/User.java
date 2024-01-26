package uz.utkirbek.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseId implements Serializable {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_active",
            nullable = false,
            columnDefinition = "boolean DEFAULT true")
    private Boolean isActive;

    public User(int id){
        super(id);
    }
}
