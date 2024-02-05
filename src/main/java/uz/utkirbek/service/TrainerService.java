package uz.utkirbek.service;

import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.service.base.BaseUpdateService;

public interface TrainerService extends BaseUpdateService<Trainer, TrainerDto> {
    Trainer getByUsername(String username);

    Boolean changeStatus(String username, Boolean isActive);
}
