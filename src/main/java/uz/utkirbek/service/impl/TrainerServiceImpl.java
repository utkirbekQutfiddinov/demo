package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainerRepository;
import uz.utkirbek.dao.UserRepository;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.service.TrainerService;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository repository;
    private final UserRepository userRepository;

    public TrainerServiceImpl(TrainerRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Trainer> getAll() {
        return repository.readAll();
    }

    @Override
    public Trainer getOne(Integer id) {
        Optional<Trainer> optional=repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public void add(Trainer bean) {
        repository.create(bean);
    }

    @Override
    public Trainer update(Trainer bean) {
        return repository.update(bean).orElse(null);
    }

    @Override
    public Trainer getByUserName(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Override
    public Boolean changePassword(Integer trainerId, String password) {
        Optional<Trainer> optional = repository.readOne(trainerId);
        if (optional.isPresent()) {
            Trainer trainer = optional.get();
            Optional<Boolean> changed = userRepository.changePassword(trainer.getUserId(), password);
            return changed.orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public Boolean changeStatus(Integer trainerId) {
        Optional<Trainer> optional = repository.readOne(trainerId);
        if (optional.isPresent()) {
            Trainer trainer = optional.get();
            Optional<Boolean> changed = userRepository.changeStatus(trainer.getUserId());
            return changed.orElse(false);
        } else {
            return false;
        }
    }
}
