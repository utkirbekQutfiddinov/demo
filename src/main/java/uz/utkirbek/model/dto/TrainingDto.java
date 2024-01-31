package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {
    private String trainerUsername;
    private String traineeUsername;
    private String name;
    private Integer duration;
    private Date date;
}
