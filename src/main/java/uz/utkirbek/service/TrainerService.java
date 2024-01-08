package uz.utkirbek.service;

import uz.utkirbek.model.Trainer;

import java.util.List;

public interface TrainerService {
    List<Trainer> getAll();

    Trainer getOne(Integer id);

    void add(Trainer bean);

    void update(Trainer bean);

}
