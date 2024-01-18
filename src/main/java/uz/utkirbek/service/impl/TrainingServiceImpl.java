package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainingRepository;
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
        Optional<Training> optional=repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public void add(Training bean) {
        repository.create(bean);
    }

}
