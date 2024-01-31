package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeUpdateDto {

    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private boolean isActive;
}
