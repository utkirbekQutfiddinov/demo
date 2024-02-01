package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.TrainerRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.TrainerService;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository repository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    public TrainerServiceImpl(TrainerRepository repository, UserRepository userRepository, TrainingRepository trainingRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Trainer> getAll() {
        return repository.findAll();
    }

    @Override
    public Trainer getOne(int id) {
        Optional<Trainer> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Trainer add(TrainerDto bean) {
        return repository.create(bean).orElse(null);
    }

    @Override
    public Trainer update(Trainer bean) {
        return repository.update(bean).orElse(null);
    }

    @Override
    public Trainer getByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Override
    public Boolean changeStatus(String username, Boolean isActive) {
        Optional<Boolean> isChanged = userRepository.changeStatus(username, isActive);
        return isChanged.orElse(false);
    }
}
