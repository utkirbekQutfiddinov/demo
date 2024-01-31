package uz.utkirbek.service;

import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.service.base.BaseService;

import java.util.List;

public interface TrainingService extends BaseService<Training, TrainingDto> {
    List<TrainingResponse> getByCriteria(TrainingFiltersDto filter);

    Boolean updateTrainer(Integer trainingId, String trainerUsername);
}
