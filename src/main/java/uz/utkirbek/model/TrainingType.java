package uz.utkirbek.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Entity
@Table(name = "training_types")
public class TrainingType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public TrainingType() {
    }

    public TrainingType(Integer id, String name){
        this.id=id;
        this.name=name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "name='" + name + '\'' +
                '}';
    }
}
