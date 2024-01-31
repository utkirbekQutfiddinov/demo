package uz.utkirbek.repository;

import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.repository.base.BaseRepository;

import java.util.Optional;

public interface TrainingTypeRepository extends BaseRepository<TrainingType, TrainingType> {
    Optional<TrainingType> findByName(String name);

    Optional<TrainingType> findByUsername(String username);
}
