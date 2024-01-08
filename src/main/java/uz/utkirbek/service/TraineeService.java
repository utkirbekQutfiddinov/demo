package uz.utkirbek.service;

import uz.utkirbek.model.Trainee;

import java.util.List;

public interface TraineeService {
    List<Trainee> getAll();

    Trainee getOne(Integer id);

    void add(Trainee bean);

    void update(Trainee bean);

    void delete(Integer id);
}
