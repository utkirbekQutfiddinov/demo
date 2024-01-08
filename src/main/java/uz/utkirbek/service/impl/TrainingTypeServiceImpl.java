package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainingTypeDao;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.service.TrainingTypeService;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    @Autowired
    private TrainingTypeDao dao;

    public List<TrainingType> getAll() {
        return dao.getAll();
    }

    public TrainingType getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(TrainingType newBean) {
        try {
            dao.add(newBean);
        } catch (Exception e) {
            System.out.println("Error on adding: "+e.getMessage());
        }
    }

    public void update(TrainingType bean) {
        try{
            dao.update(bean);
        } catch (Exception e) {
            System.out.println("Error on updating: "+e.getMessage());
        }
    }

    public void delete(Integer id) {
        try{
            dao.delete(id);
        } catch (Exception e) {
            System.out.println("Error on deleting: "+e.getMessage());
        }
    }
}
