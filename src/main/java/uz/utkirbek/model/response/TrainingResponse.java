package uz.utkirbek.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingResponse {
    private String name;
    private LocalDate date;
    private String type;
    private Integer duration;
    private String trainerUsername;
    private String traineeUsername;
}
