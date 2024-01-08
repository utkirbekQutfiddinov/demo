package uz.utkirbek.dao;

import uz.utkirbek.model.TrainingType;

import java.util.List;

public interface TrainingTypeDao {
    List<TrainingType> getAll();

    TrainingType getOne(Integer id);

    void add(TrainingType bean) throws Exception;

    void update(TrainingType bean) throws Exception;

    void delete(Integer id) throws Exception;
}
