package uz.utkirbek.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trainings")
public class Training implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "trainee_id", updatable = false)
    private Trainee trainee;

    @Column(nullable = false)
    private String name;

    @Column(name = "training_date", nullable = false)
    private Date trainingDate;

    @Column(nullable = false)
    private Integer duration;

    public Training(int id, String name, Integer duration, Date trainingDate) {
        this.id = id;
        this.name = name;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }
}
