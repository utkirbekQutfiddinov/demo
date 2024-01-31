package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingUpdateDto {
    private Integer trainingId;
    private String trainerUsername;
}
