package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.TraineeService;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    public TraineeServiceImpl(TraineeRepository repository, UserRepository userRepository, TrainingRepository trainingRepository) {
        this.traineeRepository = repository;
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public List<Trainee> getAll() {
        return traineeRepository.findAll();
    }

    @Override
    public Trainee getOne(int id) {
        Optional<Trainee> optional = traineeRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Trainee add(TraineeDto bean) {
        return traineeRepository.create(bean).orElse(null);
    }

    @Override
    public Trainee update(Trainee bean) {
        return traineeRepository.update(bean).orElse(null);
    }

    @Override
    public Boolean delete(int id) {
        Trainee trainee = getOne(id);
        if (trainee == null) {
            return false;
        }
        traineeRepository.delete(trainee);
        return true;
    }

    @Override
    public Trainee getByUsername(String username) {
        return traineeRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Boolean changePassword(int traineeId, String password) {
        Optional<Trainee> optional = traineeRepository.findById(traineeId);
        if (optional.isPresent()) {
            Trainee trainee = optional.get();
            Optional<Boolean> isChanged = userRepository.changePassword(trainee.getUser().getId(), password);
            return isChanged.orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public Boolean changeStatus(String username, Boolean isActive) {
        Optional<Boolean> isChanged = userRepository.changeStatus(username, isActive);
        return isChanged.orElse(false);
    }

    @Override
    public Boolean deleteByUsername(String username) {
        Trainee trainee = getByUsername(username);
        if (trainee == null) {
            return false;
        }
        traineeRepository.delete(trainee);
        return true;
    }

    @Override
    public List<TrainingResponse> getByCriteria(TrainingFiltersDto filter) {
        return trainingRepository.getByCriteria(filter);
    }

    @Override
    public List<TraineeTrainerResponse> getNotAssignedAcitiveTrainers(String username) {
        return traineeRepository.getNotAssignedActiveTrainers(username);
    }


}
