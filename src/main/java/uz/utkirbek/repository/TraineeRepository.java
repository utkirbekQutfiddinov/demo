package uz.utkirbek.repository;

import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.repository.base.BaseDeleteRepository;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository extends BaseDeleteRepository<Trainee, TraineeDto> {
    Optional<Trainee> findByUsername(String username);

    List<TraineeTrainerResponse> getNotAssignedActiveTrainers(String username);

    Trainee findByTrainingId(Integer trainingId);
}
