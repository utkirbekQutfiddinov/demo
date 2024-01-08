package uz.utkirbek.dao.impl;

import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TraineeDao;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TraineeDaoImpl implements TraineeDao {

    private final StorageBean storageBean;

    public TraineeDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Trainee> getAll() {
        return storageBean.getList("trainees");
    }

    public Trainee getOne(Integer id) {
        for(Trainee trainee: getAll()){
            if(trainee.getId().equals(id)){
                return trainee;
            }
        }

        return null;
    }

    public void add(Trainee bean) throws Exception {
        List<Trainee> list=getAll();
        list.add(bean);
        storageBean.setList("trainees",list);
     }

    public void update(Trainee bean) throws Exception {
        Trainee trainee=getOne(bean.getId());
        trainee.setUserId(bean.getUserId());
        trainee.setAddress(bean.getAddress());
        trainee.setBirthdate(bean.getBirthdate());
     }

    public void delete(Integer id) throws Exception {
        List<Trainee> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }

}
