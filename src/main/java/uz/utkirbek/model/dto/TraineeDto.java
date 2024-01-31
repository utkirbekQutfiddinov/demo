package uz.utkirbek.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraineeDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String address;
}
