package uz.utkirbek.service;

import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.base.BaseDeleteService;

import java.util.List;

public interface TraineeService extends BaseDeleteService<Trainee> {
    Trainee getByUsername(String username);

    Boolean changePassword(int traineeId, String password);

    Boolean changeStatus(int trainerId);

    Boolean deleteByUsername(String username);

    List<Training> getTrainingsByUsernameAndCriteria(String username);
}
