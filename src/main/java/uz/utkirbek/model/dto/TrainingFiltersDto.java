package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingFiltersDto {
    private String traineeUsername;
    private LocalDate periodFrom;
    private LocalDate periodTo;
    private String trainerUsername;
    private String trainingType;
}
