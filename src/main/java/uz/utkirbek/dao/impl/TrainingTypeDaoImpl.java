package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.enums.TABLE;
import uz.utkirbek.model.BaseIdBean;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.storage.StorageBean;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingTypeDaoImpl implements BaseDao<TrainingType> {
    static final Logger LOG = LoggerFactory.getLogger(TrainingTypeDaoImpl.class);
    private final StorageBean storageBean;

    public TrainingTypeDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<TrainingType> getAll() {
        LOG.debug("getAll: ");
        List<? extends BaseIdBean> baseIdBeans = storageBean.getList(TABLE.TRAINING_TYPES);

        List<TrainingType> trainingTypes = new ArrayList<>();
        for (BaseIdBean baseIdBean : baseIdBeans) {
            if (baseIdBean instanceof TrainingType) {
                trainingTypes.add((TrainingType) baseIdBean);
            }
        }

        return trainingTypes;
    }

    public TrainingType getOne(Integer id) {
        LOG.debug("getOne: "+id);

        for(TrainingType trainingType: getAll()){
            if(trainingType.getId()==id){
                return trainingType;
            }
        }

        return null;
    }

    public void add(TrainingType bean) throws Exception {
        LOG.debug("add: "+bean);

        List<TrainingType> list=getAll();
        bean.setId(bean.getId()==0?list.size():bean.getId());
        list.add(bean);
        storageBean.setList(TABLE.TRAINING_TYPES,new ArrayList<>(list));
    }

    public void update(TrainingType bean) throws Exception {
        LOG.debug("update: "+bean);

        TrainingType trainingType=getOne(bean.getId());
        trainingType.setName(bean.getName());
    }

    public void delete(Integer id) throws Exception {
        LOG.debug("delete: "+id);

        List<TrainingType> list=getAll();

        list.removeIf(t -> t.getId()==id);
        storageBean.setList(TABLE.TRAINING_TYPES,new ArrayList<>(list));
    }
}
