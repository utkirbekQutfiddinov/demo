package uz.utkirbek.dao.impl;

import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingDao;
import uz.utkirbek.model.Training;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private final StorageBean storageBean;

    public TrainingDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Training> getAll() {
        return storageBean.getList("trainings");
    }

    public Training getOne(Integer id) {
        for(Training training: getAll()){
            if(training.getId().equals(id)){
                return training;
            }
        }

        return null;
    }

    public void add(Training bean) throws Exception {
        List<Training> list=getAll();
        list.add(bean);
        storageBean.setList("trainings",list);
    }

    public void update(Training bean) throws Exception {
        Training training=getOne(bean.getId());
        training.setTrainingTypeId(bean.getTrainingTypeId());
        training.setDuration(bean.getDuration());
        training.setDate(bean.getDate());
        training.setTrainerId(bean.getTrainerId());
        training.setTraineeId(bean.getTraineeId());
        training.setName(bean.getName());

    }

    public void delete(Integer id) throws Exception {
        List<Training> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }
}
