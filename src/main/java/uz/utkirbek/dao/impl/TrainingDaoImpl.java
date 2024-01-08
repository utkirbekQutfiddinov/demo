package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingDao;
import uz.utkirbek.model.Training;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    static final Logger LOG = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private final StorageBean storageBean;

    public TrainingDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Training> getAll() {
        LOG.info("getAll: ");

        return storageBean.getList("trainings");
    }

    public Training getOne(Integer id) {
        LOG.info("getOne: "+id);

        for(Training training: getAll()){
            if(training.getId().equals(id)){
                return training;
            }
        }

        return null;
    }

    public void add(Training bean) throws Exception {
        LOG.info("add: "+bean);

        List<Training> list=getAll();
        list.add(bean);
        storageBean.setList("trainings",list);
    }

    public void update(Training bean) throws Exception {
        LOG.info("update: "+bean);

        Training training=getOne(bean.getId());
        training.setTrainingTypeId(bean.getTrainingTypeId());
        training.setDuration(bean.getDuration());
        training.setDate(bean.getDate());
        training.setTrainerId(bean.getTrainerId());
        training.setTraineeId(bean.getTraineeId());
        training.setName(bean.getName());

    }

    public void delete(Integer id) throws Exception {
        LOG.info("delete: "+id);

        List<Training> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }
}
