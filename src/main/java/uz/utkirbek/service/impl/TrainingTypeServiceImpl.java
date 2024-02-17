package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.repository.TrainingTypeRepository;
import uz.utkirbek.service.TrainingTypeService;

import java.util.Collections;
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
        try {
            List<TrainingType> all = repository.findAll();
            return all;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public TrainingType getOne(int id) {
        try {
            Optional<TrainingType> optional = repository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TrainingType add(TrainingType bean) {
        try {
            Optional<TrainingType> trainingType = repository.create(bean);
            return trainingType.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TrainingType getByName(String name) {
        try {
            Optional<TrainingType> optional = repository.findByName(name);
            return optional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
