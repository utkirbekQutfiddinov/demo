package uz.utkirbek.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class BaseId {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public BaseId(int id){
        this.id=id;
    }
}
