package uz.utkirbek.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.utkirbek.model.entity.TrainingType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponse {
    private String firstName;
    private String lastName;
    private boolean isActive;
    private TrainingType specialization;

    private List<TrainerTraineeResponse> traineesList;

}
