package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Training;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.TraineeService;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository repository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    public TraineeServiceImpl(TraineeRepository repository, UserRepository userRepository, TrainingRepository trainingRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Trainee> getAll() {
        return repository.findAll();
    }

    @Override
    public Trainee getOne(int id) {
        Optional<Trainee> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Trainee add(Trainee bean) {
        return repository.create(bean).orElse(null);
    }

    @Override
    public Trainee update(Trainee bean) {
        return repository.update(bean).orElse(null);
    }

    @Override
    public Boolean delete(int id) {
        Trainee trainee = getOne(id);
        if (trainee == null) {
            return false;
        }
        repository.delete(trainee);
        return true;
    }

    @Override
    public Trainee getByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Override
    public Boolean changePassword(int traineeId, String password) {
        Optional<Trainee> optional = repository.findById(traineeId);
        if (optional.isPresent()) {
            Trainee trainee = optional.get();
            Optional<Boolean> isChanged = userRepository.changePassword(trainee.getUser().getId(), password);
            return isChanged.orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public Boolean changeStatus(int trainerId) {
        Optional<Trainee> optional = repository.findById(trainerId);
        if (optional.isPresent()) {
            Trainee trainee = optional.get();
            Optional<Boolean> isChanged = userRepository.changeStatus(trainee.getUser().getId());
            return isChanged.orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteByUsername(String username) {
        Trainee trainee = getByUsername(username);
        if (trainee == null) {
            return false;
        }
        repository.delete(trainee);
        return true;
    }

    @Override
    public List<Training> getTrainingsByUsernameAndCriteria(String username) {
        return trainingRepository.getByUsernameAndCriteria(username);
    }


}
