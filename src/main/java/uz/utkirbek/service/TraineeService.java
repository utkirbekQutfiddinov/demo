package uz.utkirbek.service;

import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.base.BaseDeleteService;

import java.util.List;

public interface TraineeService extends BaseDeleteService<Trainee, Integer> {
    Trainee getByUsername(String username);
    Boolean changePassword(Integer traineeId, String password);
    Boolean changeStatus(Integer trainerId);
    Boolean deleteByUsername(String username);
    List<Training> getTrainingsByUsernameAndCriteria(String username);
}
