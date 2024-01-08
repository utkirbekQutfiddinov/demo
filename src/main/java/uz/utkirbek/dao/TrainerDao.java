package uz.utkirbek.dao;

import uz.utkirbek.model.Trainer;

import java.util.List;

public interface TrainerDao {
    List<Trainer> getAll();

    Trainer getOne(Integer id);

    void add(Trainer bean) throws Exception;

    void update(Trainer bean) throws Exception;

    void delete(Integer id) throws Exception;
}
