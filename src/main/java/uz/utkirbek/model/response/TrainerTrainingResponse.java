package uz.utkirbek.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingResponse {
    private String name;
    private Date date;
    private String type;
    private Integer duration;
    private String traineeUsername;
}
