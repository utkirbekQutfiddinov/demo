package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainingTypeRepository;
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
    public TrainingType getOne(Integer id) {
        Optional<TrainingType> optional=repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public void add(TrainingType bean) {
        repository.create(bean);
    }

    @Override
    public void update(TrainingType bean) {
        repository.update(bean);
    }

    @Override
    public void delete(Integer id) {
        TrainingType type=getOne(id);
        if (type!=null)
            repository.delete(type);
    }
}
