package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TraineeRepository;
import uz.utkirbek.dao.UserRepository;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.service.TraineeService;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository repository;
    private final UserRepository userRepository;

    public TraineeServiceImpl(TraineeRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Trainee> getAll() {
        return repository.readAll();
    }

    @Override
    public Trainee getOne(Integer id) {
        Optional<Trainee> optional = repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public void add(Trainee bean) {
        repository.create(bean);
    }

    @Override
    public void update(Trainee bean) {
        repository.update(bean);
    }

    @Override
    public void delete(Integer id) {
        Trainee trainee = getOne(id);
        if (trainee != null)
            repository.delete(trainee);
    }

    @Override
    public Trainee getByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Override
    public Boolean changePassword(Integer traineeId, String password) {
        Optional<Trainee> optional = repository.readOne(traineeId);
        if (optional.isPresent()) {
            Trainee trainee = optional.get();
            Optional<Boolean> changed = userRepository.changePassword(trainee.getUserId(), password);
            return changed.orElse(false);
        } else {
            return false;
        }
    }

    @Override
    public Boolean changeStatus(Integer trainerId) {
        Optional<Trainee> optional = repository.readOne(trainerId);
        if (optional.isPresent()) {
            Trainee trainee = optional.get();
            Optional<Boolean> changed = userRepository.changeStatus(trainee.getUserId());
            return changed.orElse(false);
        } else {
            return false;
        }
    }


}
