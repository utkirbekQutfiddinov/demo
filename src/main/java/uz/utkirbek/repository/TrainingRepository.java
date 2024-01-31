package uz.utkirbek.repository;

import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends BaseRepository<Training, TrainingDto> {
    Optional<Boolean> updateTrainer(Integer trainingId, String trainerUsername);

    List<TrainingResponse> getByCriteria(TrainingFiltersDto filter);
}
