package uz.utkirbek.service;

import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.service.base.BaseDeleteService;

import java.util.List;

public interface TraineeService extends BaseDeleteService<Trainee, TraineeDto> {
    Trainee getByUsername(String username);

    Boolean changePassword(int traineeId, String password);

    Boolean changeStatus(String username, Boolean isActive);

    Boolean deleteByUsername(String username);

    List<TrainingResponse> getByCriteria(TrainingFiltersDto filter);

    List<TraineeTrainerResponse> getNotAssignedAcitiveTrainers(String username);
}
