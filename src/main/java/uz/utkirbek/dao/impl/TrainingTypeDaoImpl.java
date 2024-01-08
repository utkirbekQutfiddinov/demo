package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingTypeDao;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    static final Logger LOG = LoggerFactory.getLogger(TrainingTypeDaoImpl.class);
    private final StorageBean storageBean;

    public TrainingTypeDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<TrainingType> getAll() {
        LOG.info("getAll: ");

        return storageBean.getList("trainingTypes");
    }

    public TrainingType getOne(Integer id) {
        LOG.info("getOne: "+id);

        for(TrainingType trainingType: getAll()){
            if(trainingType.getId().equals(id)){
                return trainingType;
            }
        }

        return null;
    }

    public void add(TrainingType bean) throws Exception {
        LOG.info("add: "+bean);

        List<TrainingType> list=getAll();
        list.add(bean);
        storageBean.setList("trainingTypes",list);
    }

    public void update(TrainingType bean) throws Exception {
        LOG.info("update: "+bean);

        TrainingType trainingType=getOne(bean.getId());
        trainingType.setName(bean.getName());
    }

    public void delete(Integer id) throws Exception {
        LOG.info("delete: "+id);

        List<TrainingType> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }
}
