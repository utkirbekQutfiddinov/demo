package uz.utkirbek.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "trainers")
public class Trainer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private TrainingType trainingType;

    @Column(name = "training_type_id")
    private Integer specialization;

    public Trainer() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Integer specialization) {
        this.specialization = specialization;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "userId=" + userId +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
