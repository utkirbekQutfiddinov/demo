package uz.utkirbek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class TraineeServiceImpl implements BaseService<Trainee> {
    static final Logger LOG = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    @Qualifier(value = "traineeDaoImpl")
    private BaseDao<Trainee> dao;

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
            LOG.debug("Error on adding");
        }
    }

    public void update(Trainee bean) {
        try{
            dao.update(bean);
        } catch (Exception e) {
            LOG.debug("Error on updating");
        }
    }

    public void delete(Integer id) {
        try{
            dao.delete(id);
        } catch (Exception e) {
            LOG.debug("Error on deleting");
        }
    }
}
