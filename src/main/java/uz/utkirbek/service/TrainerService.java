package uz.utkirbek.service;

import uz.utkirbek.model.Trainer;

import java.util.Optional;

public interface TrainerService extends BaseService<Trainer>{
    Trainer getByUserName(String username);
    Boolean changePassword(Integer trainerId, String password);
    Boolean changeStatus(Integer trainerId);
}
