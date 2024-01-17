package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TraineeRepository;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.service.TraineeService;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final TraineeRepository repository;

    public TraineeServiceImpl(TraineeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Trainee> getAll() {
        return repository.readAll();
    }

    @Override
    public Trainee getOne(Integer id) {
        Optional<Trainee> optional=repository.readOne(id);
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
        Trainee trainee=getOne(id);
        if (trainee!=null)
            repository.delete(trainee);
    }
}
