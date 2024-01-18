package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class TrainingServiceImpl implements BaseService<Training> {

    @Autowired
    @Qualifier(value = "trainingDaoImpl")
    private BaseDao<Training> dao;

    public List<Training> getAll() {
        return dao.getAll();
    }

    public Training getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(Training newBean) {
        dao.add(newBean);
    }

}
