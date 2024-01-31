package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.service.TrainingService;

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
        return repository.findAll();
    }

    @Override
    public Training getOne(int id) {
        Optional<Training> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Training add(TrainingDto bean) {
        return repository.create(bean).orElse(null);
    }

    @Override
    public List<TrainingResponse> getByCriteria(TrainingFiltersDto filter) {
        return repository.getByCriteria(filter);
    }

    @Override
    public Boolean updateTrainer(Integer trainingId, String trainerUsername) {
        return repository.updateTrainer(trainingId, trainerUsername).orElse(false);
    }
}
