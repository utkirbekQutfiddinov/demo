package uz.utkirbek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements BaseService<TrainingType> {
    static final Logger LOG = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

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
        try {
            dao.add(newBean);
        } catch (Exception e) {
            LOG.debug("Error on adding: "+e.getMessage());
        }
    }

    public void update(TrainingType bean) {
        try{
            dao.update(bean);
        } catch (Exception e) {
            LOG.debug("Error on updating: "+e.getMessage());
        }
    }

    public void delete(Integer id) {
        try{
            dao.delete(id);
        } catch (Exception e) {
            LOG.debug("Error on deleting: "+e.getMessage());
        }
    }
}
