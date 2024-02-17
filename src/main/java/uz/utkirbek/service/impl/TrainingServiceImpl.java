package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.service.TrainingService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository repository;

    public TrainingServiceImpl(TrainingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Training> getAll() {
        try {
            List<Training> all = repository.findAll();
            return all;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Training getOne(int id) {
        try {
            Optional<Training> optional = repository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Training add(TrainingDto bean) {
        try {
            Optional<Training> training = repository.create(bean);
            return training.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TrainingResponse> getByCriteria(TrainingFiltersDto filter) {
        try {
            List<TrainingResponse> byCriteria = repository.getByCriteria(filter);
            return byCriteria;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean updateTrainer(Integer trainingId, String trainerUsername) {
        try {
            Optional<Boolean> aBoolean = repository.updateTrainer(trainingId, trainerUsername);
            return aBoolean.orElse(false);
        } catch (Exception e) {
            return false;
        }
    }
}
