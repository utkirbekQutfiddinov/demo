package uz.utkirbek.repository;

import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends BaseRepository<Training> {
    Optional<Boolean> updateTrainer(Integer trainingId, Trainer trainer);
    List<Training> getByUsernameAndCriteria(String username);
}
