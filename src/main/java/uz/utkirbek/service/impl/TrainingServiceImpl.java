package uz.utkirbek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.Training;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class TrainingServiceImpl implements BaseService<Training> {
    static final Logger LOG = LoggerFactory.getLogger(TrainingServiceImpl.class);

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
        try {
            dao.add(newBean);
        } catch (Exception e) {
            LOG.debug("Error on adding");
        }
    }

}
