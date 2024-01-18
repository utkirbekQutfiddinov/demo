package uz.utkirbek.service.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Service;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.model.Training;
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
        return repository.readAll();
    }

    @Override
    public Training getOne(Integer id) {
        Optional<Training> optional = repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public Training add(Training bean) {
        return repository.create(bean).orElse(null);
    }

    @Override
    public List<Training> getByUsernameAndCriteria(String username) {
        return repository.getByUsernameAndCriteria(username);
    }

    @Override
    public Boolean updateTrainer(Integer trainingId, Trainer trainer) {
        return repository.updateTrainer(trainingId,trainer).orElse(false);
    }
}
