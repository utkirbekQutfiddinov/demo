package uz.utkirbek.dao.impl;

import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingTypeDao;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    private final StorageBean storageBean;

    public TrainingTypeDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<TrainingType> getAll() {
        return storageBean.getList("trainingTypes");
    }

    public TrainingType getOne(Integer id) {
        for(TrainingType trainingType: getAll()){
            if(trainingType.getId().equals(id)){
                return trainingType;
            }
        }

        return null;
    }

    public void add(TrainingType bean) throws Exception {
        List<TrainingType> list=getAll();
        list.add(bean);
        storageBean.setList("trainingTypes",list);
    }

    public void update(TrainingType bean) throws Exception {
        TrainingType trainingType=getOne(bean.getId());
        trainingType.setName(bean.getName());

    }

    public void delete(Integer id) throws Exception {
        List<TrainingType> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }
}
