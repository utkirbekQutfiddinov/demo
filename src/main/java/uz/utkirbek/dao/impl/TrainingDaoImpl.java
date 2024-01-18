package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.enums.Table;
import uz.utkirbek.model.BaseIdBean;
import uz.utkirbek.model.Training;
import uz.utkirbek.storage.StorageBean;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingDaoImpl implements BaseDao<Training> {
    static final Logger LOG = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private final StorageBean storageBean;

    public TrainingDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Training> getAll() {
        LOG.debug("getAll: ");
        List<? extends BaseIdBean> baseIdBeans = storageBean.getList(Table.TRAININGS);

        List<Training> trainings = new ArrayList<>();
        for (BaseIdBean baseIdBean : baseIdBeans) {
            if (baseIdBean instanceof Training) {
                trainings.add((Training) baseIdBean);
            }
        }

        return trainings;
    }

    public Training getOne(Integer id) {
        LOG.debug("getOne: " + id);

        for (Training training : getAll()) {
            if (training.getId() == id) {
                return training;
            }
        }

        return null;
    }

    public void add(Training bean) {
        LOG.debug("add: " + bean);

        List<Training> list = getAll();
        bean.setId(bean.getId() == 0 ? list.size() : bean.getId());
        list.add(bean);
        storageBean.setList(Table.TRAININGS, new ArrayList<>(list));
    }
}
