package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.enums.TABLE;
import uz.utkirbek.model.BaseIdBean;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.storage.StorageBean;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainerDaoImpl implements BaseDao<Trainer> {
    static final Logger LOG = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private final StorageBean storageBean;

    public TrainerDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Trainer> getAll() {
        LOG.debug("getAll: ");
        List<? extends BaseIdBean> baseIdBeans = storageBean.getList(TABLE.TRAINERS);

        List<Trainer> trainers = new ArrayList<>();
        for (BaseIdBean baseIdBean : baseIdBeans) {
            if (baseIdBean instanceof Trainer) {
                trainers.add((Trainer) baseIdBean);
            }
        }

        return trainers;
    }

    public Trainer getOne(Integer id) {
        LOG.debug("getOne: "+id);

        for(Trainer trainer: getAll()){
            if(trainer.getId()==id){
                return trainer;
            }
        }

        return null;
    }

    public void add(Trainer bean) throws Exception {
        LOG.debug("add: "+bean);

        List<Trainer> list=getAll();
        bean.setId(bean.getId()==0?list.size():bean.getId());
        list.add(bean);
        storageBean.setList(TABLE.TRAINERS,new ArrayList<>(list));
    }

    public void update(Trainer bean) throws Exception {
        LOG.debug("update: "+bean);

        Trainer trainer=getOne(bean.getId());
        trainer.setUserId(bean.getUserId());
        trainer.setSpecialization(bean.getSpecialization());

    }

    public void delete(Integer id) throws Exception {
        LOG.debug("delete: "+id);

        List<Trainer> list=getAll();

        list.removeIf(t -> t.getId()==id);
        storageBean.setList(TABLE.TRAINERS,new ArrayList<>(list));
    }



}
