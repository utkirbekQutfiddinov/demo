package uz.utkirbek.dao;

import uz.utkirbek.model.Trainee;

import java.util.List;

public interface TraineeDao {
    List<Trainee> getAll();

    Trainee getOne(Integer id);

    void add(Trainee bean) throws Exception;

    void update(Trainee bean) throws Exception;

    void delete(Integer id) throws Exception;
}
