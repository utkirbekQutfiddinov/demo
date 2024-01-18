package uz.utkirbek.service;

import uz.utkirbek.model.Trainer;
import uz.utkirbek.service.base.BaseUpdateService;

public interface TrainerService extends BaseUpdateService<Trainer, Integer> {
    Trainer getByUserName(String username);
    Boolean changePassword(Integer trainerId, String password);
    Boolean changeStatus(Integer trainerId);
}
