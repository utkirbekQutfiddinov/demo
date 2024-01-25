package uz.utkirbek.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "training_types")
public class TrainingType extends BaseId implements Serializable {

    @Column(nullable = false)
    private String name;
}
