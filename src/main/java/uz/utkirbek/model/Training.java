package uz.utkirbek.model;

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
    private Integer id;

    @Column(name = "trainer_id")
    private Integer trainerId;

    @ManyToOne
    @JoinColumn(name = "trainer_id", insertable = false, updatable = false)
    private Trainer trainer;

    @Column(name = "trainee_id")
    private Integer traineeId;

    @ManyToOne
    @JoinColumn(name = "trainee_id", insertable = false, updatable = false)
    private Trainee trainee;

    @Column(nullable = false)
    private String name;

    @Column(name = "training_type_id")
    private Integer trainingTypeId;

    @ManyToOne
    @JoinColumn(name = "training_type_id", insertable = false, updatable = false)
    private TrainingType trainingType;

    @Column(name = "training_date", nullable = false)
    private Date trainingDate;

    @Column(nullable = false)
    private Integer duration;

}
