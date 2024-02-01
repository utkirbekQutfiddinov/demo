package uz.utkirbek.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingResponse {
    private String name;
    private Date date;
    private String type;
    private Integer duration;
    private String trainerUsername;
    private String traineeUsername;
}
