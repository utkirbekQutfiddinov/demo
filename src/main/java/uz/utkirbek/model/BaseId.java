package uz.utkirbek.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BaseId {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
