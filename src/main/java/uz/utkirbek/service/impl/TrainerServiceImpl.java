package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.TrainerDao;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.service.TrainerService;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDao dao;

    public TrainerServiceImpl(TrainerDao dao) {
        this.dao = dao;
    }

    public List<Trainer> getAll() {
        return dao.getAll();
    }

    public Trainer getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(Trainer newBean) {
        try {
            dao.add(newBean);
        } catch (Exception e) {
            System.out.println("Error on adding");
        }
    }

    public void update(Trainer bean) {
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
