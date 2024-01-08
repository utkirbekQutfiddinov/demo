package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainerDao;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    static final Logger LOG = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private final StorageBean storageBean;

    public TrainerDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Trainer> getAll() {
        LOG.info("getAll: ");

        return storageBean.getList("trainers");
    }

    public Trainer getOne(Integer id) {
        LOG.info("getOne: "+id);

        for(Trainer trainer: getAll()){
            if(trainer.getId().equals(id)){
                return trainer;
            }
        }

        return null;
    }

    public void add(Trainer bean) throws Exception {
        LOG.info("add: "+bean);

        List<Trainer> list=getAll();
        list.add(bean);
        storageBean.setList("trainers",list);
    }

    public void update(Trainer bean) throws Exception {
        LOG.info("update: "+bean);

        Trainer trainer=getOne(bean.getId());
        trainer.setUserId(bean.getUserId());
        trainer.setSpecialization(bean.getSpecialization());

    }

    public void delete(Integer id) throws Exception {
        LOG.info("delete: "+id);

        List<Trainer> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }



}
