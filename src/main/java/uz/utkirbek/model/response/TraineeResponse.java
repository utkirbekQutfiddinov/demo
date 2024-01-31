package uz.utkirbek.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeResponse {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private boolean isActive;

    private List<TraineeTrainerResponse> trainersList;

}
