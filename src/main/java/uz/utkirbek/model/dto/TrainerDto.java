package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.utkirbek.model.entity.TrainingType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerDto {
    private String firstName;
    private String lastName;
    private TrainingType specialization;
}
