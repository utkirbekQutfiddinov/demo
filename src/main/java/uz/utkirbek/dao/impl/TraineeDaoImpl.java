package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TraineeDao;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TraineeDaoImpl implements TraineeDao {
    static final Logger LOG = LoggerFactory.getLogger(TraineeDaoImpl.class);

    private final StorageBean storageBean;

    public TraineeDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Trainee> getAll() {
        LOG.info("getAll: ");

        return storageBean.getList("trainees");

    }

    public Trainee getOne(Integer id) {
        LOG.info("getOne: "+id);

        for(Trainee trainee: getAll()){
            if(trainee.getId().equals(id)){
                return trainee;
            }
        }

        return null;
    }

    public void add(Trainee bean) throws Exception {
        LOG.info("add: "+bean);

        List<Trainee> list=getAll();
        list.add(bean);
        storageBean.setList("trainees",list);
     }

    public void update(Trainee bean) throws Exception {
        LOG.info("update: "+bean);

        Trainee trainee=getOne(bean.getId());
        trainee.setUserId(bean.getUserId());
        trainee.setAddress(bean.getAddress());
        trainee.setBirthdate(bean.getBirthdate());
     }

    public void delete(Integer id) throws Exception {
        LOG.info("delete: "+id);

        List<Trainee> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }

}
