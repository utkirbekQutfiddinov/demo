package uz.utkirbek.model;

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
public class Trainee extends BaseId implements Serializable {

    @Column(length = 1000)
    private String address;

    @Column(name = "birth_date")
    private Date birthdate;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainee_id", updatable = false, insertable = false)
    private List<Training> trainings;
}
