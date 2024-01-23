package uz.utkirbek.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
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

}
