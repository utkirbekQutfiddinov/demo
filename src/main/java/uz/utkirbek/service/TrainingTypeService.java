package uz.utkirbek.service;

import uz.utkirbek.model.TrainingType;

import java.util.List;

public interface TrainingTypeService {
    List<TrainingType> getAll();

    TrainingType getOne(Integer id);

    void add(TrainingType bean);

    void update(TrainingType bean);

    void delete(Integer id);
}
