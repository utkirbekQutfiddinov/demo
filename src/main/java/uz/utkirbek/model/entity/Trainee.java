package uz.utkirbek.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trainees")
public class Trainee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 1000)
    private String address;

    @Column(name = "birth_date")
    private Date birthdate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainee_id", updatable = false)
    private List<Training> trainings;

    public Trainee(User user, String address, Date birthdate) {
        this.address = address;
        this.user = user;
        this.birthdate = birthdate;
    }
}
