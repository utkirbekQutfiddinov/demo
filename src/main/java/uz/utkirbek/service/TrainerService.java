package uz.utkirbek.service;

import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.service.base.BaseUpdateService;

import java.util.List;

public interface TrainerService extends BaseUpdateService<Trainer, TrainerDto> {
    Trainer getByUsername(String username);

    Boolean changePassword(Integer trainerId, String password);

    Boolean changeStatus(String username, Boolean isActive);

    List<TrainingResponse> getByCriteria(TrainingFiltersDto filter);
}
