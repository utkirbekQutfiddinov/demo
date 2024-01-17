package uz.utkirbek.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Table(name = "trainers")
public class Trainer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;
    private String specialization;

    public Trainer() {
    }

    public Trainer(int userId, String spec) {
        this.id=id;
        this.specialization=spec;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "userId=" + userId +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
