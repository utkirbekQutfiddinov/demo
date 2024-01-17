package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainerRepository;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.service.TrainerService;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository repository;

    public TrainerServiceImpl(TrainerRepository repository) {
        this.repository = repository;
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
    public void update(Trainer bean) {
        repository.update(bean);
    }

    @Override
    public void delete(Integer id) {
        Trainer trainer=getOne(id);
        if (trainer!=null)
            repository.delete(trainer);
    }
}
