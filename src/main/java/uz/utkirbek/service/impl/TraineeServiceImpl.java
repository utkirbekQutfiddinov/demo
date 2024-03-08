package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.security.JwtFilter;
import uz.utkirbek.service.TraineeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;

    public TraineeServiceImpl(TraineeRepository repository, UserRepository userRepository) {
        this.traineeRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Trainee> getAll() {
        return traineeRepository.findAll();
    }

    @Override
    public Trainee getOne(int id) {
        try {
            Optional<Trainee> optional = traineeRepository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Trainee add(TraineeDto bean) {
        try {
            Optional<Trainee> trainee = traineeRepository.create(bean);
            return trainee.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Trainee update(Trainee bean) {
        try {
            Optional<Trainee> update = traineeRepository.update(bean);
            return update.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {
        try {
            Trainee trainee = getOne(id);
            if (trainee == null) {
                return false;
            }
            traineeRepository.delete(trainee);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Trainee getByUsername(String username) {
        try {
            Optional<Trainee> byUsername = traineeRepository.findByUsername(username);
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

    @Override
    public List<TraineeTrainerResponse> getNotAssignedAcitiveTrainers(String username) {
        try {
            List<TraineeTrainerResponse> notAssignedActiveTrainers = traineeRepository.getNotAssignedActiveTrainers(username);
            return notAssignedActiveTrainers;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


}
