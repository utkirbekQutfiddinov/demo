package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TraineeDao;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.service.TraineeService;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeDao dao;

    public List<Trainee> getAll() {
        return dao.getAll();
    }

    public Trainee getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(Trainee newBean) {
        try {
            dao.add(newBean);
        } catch (Exception e) {
            System.out.println("Error on adding");
        }
    }

    public void update(Trainee bean) {
        try{
            dao.update(bean);
        } catch (Exception e) {
            System.out.println("Error on updating");
        }
    }

    public void delete(Integer id) {
        try{
            dao.delete(id);
        } catch (Exception e) {
            System.out.println("Error on deleting");
        }
    }
}
