package uz.utkirbek.dao;

import uz.utkirbek.model.Training;

import java.util.List;

public interface TrainingDao {
    List<Training> getAll();

    Training getOne(Integer id);

    void add(Training bean) throws Exception;

    void update(Training bean) throws Exception;

    void delete(Integer id) throws Exception;
}
