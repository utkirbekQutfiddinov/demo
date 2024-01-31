package uz.utkirbek.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_active",
            nullable = false,
            columnDefinition = "boolean DEFAULT true")
    private boolean isActive;

    public User(int id) {
        this.id = id;
    }

    public User(String firstName, String lastName) {
        this.firstname = firstName;
        this.lastname = lastName;
    }
}
