package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainingDao;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.TrainingService;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDao dao;

    public List<Training> getAll() {
        return dao.getAll();
    }

    public Training getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(Training newBean) {
        try {
            dao.add(newBean);
        } catch (Exception e) {
            System.out.println("Error on adding");
        }
    }

}
