package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.repository.TrainerRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.TrainerService;

import java.util.Collections;
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
        try {
            List<Trainer> all = repository.findAll();
            return all;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Trainer getOne(int id) {
        try {
            Optional<Trainer> optional = repository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Trainer add(TrainerDto bean) {
        try {
            Optional<Trainer> trainerOptional = repository.create(bean);
            return trainerOptional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Trainer update(Trainer bean) {
        try {
            Optional<Trainer> update = repository.update(bean);
            return update.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Trainer getByUsername(String username) {
        try {
            Optional<Trainer> byUsername = repository.findByUsername(username);
            return byUsername.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean changeStatus(String username, Boolean isActive) {
        try {
            Optional<Boolean> isChanged = userRepository.changeStatus(username, isActive);
            return isChanged.orElse(false);
        } catch (Exception e) {
            return false;
        }
    }
}
