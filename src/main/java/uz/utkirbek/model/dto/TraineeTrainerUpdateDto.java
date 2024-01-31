package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainerUpdateDto {
    private String username;
    private List<String> trainers;
}
