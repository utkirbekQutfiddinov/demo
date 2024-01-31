package uz.utkirbek.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "training_types")
public class TrainingType implements Serializable {
    @Id
    private int id;

    @Column(nullable = false)
    private String name;
}
