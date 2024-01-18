package uz.utkirbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements BaseService<TrainingType> {

    @Autowired
    @Qualifier(value = "trainingTypeDaoImpl")
    private BaseDao<TrainingType> dao;

    public List<TrainingType> getAll() {
        return dao.getAll();
    }

    public TrainingType getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(TrainingType newBean) {
        dao.add(newBean);

    }
}
