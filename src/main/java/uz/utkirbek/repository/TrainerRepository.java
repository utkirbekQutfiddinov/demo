package uz.utkirbek.repository;

import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.repository.base.BaseUpdateRepository;

import java.util.Optional;

public interface TrainerRepository extends BaseUpdateRepository<Trainer, TrainerDto> {
    Optional<Trainer> findByUsername(String username);

    Trainer findByTrainingId(Integer trainingId);
}
