package uz.utkirbek.service;

import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.base.BaseService;

import java.util.List;

public interface TrainingService extends BaseService<Training> {
    List<Training> getByUsernameAndCriteria(String username);

    Boolean updateTrainer(int trainingId, Trainer trainer);
}
