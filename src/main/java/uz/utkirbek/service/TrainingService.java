package uz.utkirbek.service;

import uz.utkirbek.model.Training;

import java.util.List;

public interface TrainingService {
    List<Training> getAll();

    Training getOne(Integer id);

    void add(Training bean);
}
