package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.repository.TrainingTypeRepository;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.service.TrainingTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeRepository repository;

    public TrainingTypeServiceImpl(TrainingTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TrainingType> getAll() {
        return repository.readAll();
    }

    @Override
    public TrainingType getOne(int id) {
        Optional<TrainingType> optional=repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public TrainingType add(TrainingType bean) {
        return repository.create(bean).orElse(null);
    }

}
