package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDeleteDao;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.service.BaseDeleteService;

import java.util.List;

@Service
public class TraineeServiceImpl implements BaseDeleteService<Trainee> {

    @Autowired
    @Qualifier(value = "traineeDaoImpl")
    private BaseDeleteDao<Trainee> dao;

    public List<Trainee> getAll() {
        return dao.getAll();
    }

    public Trainee getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(Trainee newBean) {
        dao.add(newBean);
    }

    public void update(Trainee bean) {
        dao.update(bean);
    }

    public void delete(Integer id) {
        dao.delete(id);
    }
}
