package uz.utkirbek.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.utkirbek.model.entity.TrainingType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainerResponse {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingType specialization;

    public TraineeTrainerResponse(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
