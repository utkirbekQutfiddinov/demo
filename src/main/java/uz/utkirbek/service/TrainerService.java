package uz.utkirbek.service;

import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.base.BaseUpdateService;

import java.util.List;

public interface TrainerService extends BaseUpdateService<Trainer, Integer> {
    Trainer getByUserName(String username);
    Boolean changePassword(Integer trainerId, String password);
    Boolean changeStatus(Integer trainerId);
    List<Trainer> getNotAssignedAndActive();
    List<Training> getTrainingsByUsernameAndCriteria(String username);
}
