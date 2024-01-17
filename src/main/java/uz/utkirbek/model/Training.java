package uz.utkirbek.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

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


    public Training() {
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Integer trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainerId=" + trainerId +
                ", trainer=" + trainer +
                ", traineeId=" + traineeId +
                ", trainee=" + trainee +
                ", name='" + name + '\'' +
                ", trainingTypeId=" + trainingTypeId +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", duration=" + duration +
                '}';
    }
}
