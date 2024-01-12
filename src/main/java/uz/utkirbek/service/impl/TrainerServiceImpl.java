package uz.utkirbek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class TrainerServiceImpl implements BaseService<Trainer> {
    static final Logger LOG = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    @Qualifier(value = "trainerDaoImpl")
    private BaseDao<Trainer> dao;


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
            LOG.debug("Error on adding");
        }
    }

    public void update(Trainer bean) {
        try{
            dao.update(bean);
        } catch (Exception e) {
            LOG.debug("Error on updating");
        }
    }

}
