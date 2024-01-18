package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseUpdateDao;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.service.BaseUpdateService;

import java.util.List;

@Service
public class TrainerServiceImpl implements BaseUpdateService<Trainer> {

    @Autowired
    @Qualifier(value = "trainerDaoImpl")
    private BaseUpdateDao<Trainer> dao;

    public List<Trainer> getAll() {
        return dao.getAll();
    }

    public Trainer getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(Trainer newBean) {
        dao.add(newBean);
    }

    public void update(Trainer bean) {
        dao.update(bean);
    }

}
