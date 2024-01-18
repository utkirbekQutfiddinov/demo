package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.BaseDeleteDao;
import uz.utkirbek.enums.Table;
import uz.utkirbek.model.BaseIdBean;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.storage.StorageBean;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TraineeDaoImpl implements BaseDeleteDao<Trainee> {
    static final Logger LOG = LoggerFactory.getLogger(TraineeDaoImpl.class);

    private final StorageBean storageBean;

    public TraineeDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<Trainee> getAll() {
        LOG.debug("getAll: ");
        List<? extends BaseIdBean> baseIdBeans = storageBean.getList(Table.TRAINEES);

        List<Trainee> trainees = new ArrayList<>();
        for (BaseIdBean baseIdBean : baseIdBeans) {
            if (baseIdBean instanceof Trainee) {
                trainees.add((Trainee) baseIdBean);
            }
        }

        return trainees;

    }

    public Trainee getOne(Integer id) {
        LOG.debug("getOne: " + id);

        for (Trainee trainee : getAll()) {
            if (trainee.getId() == id) {
                return trainee;
            }
        }

        return null;
    }

    public void add(Trainee bean) {
        LOG.debug("add: " + bean);

        List<Trainee> list = getAll();
        bean.setId(bean.getId() == 0 ? list.size() : bean.getId());
        list.add(bean);
        storageBean.setList(Table.TRAINEES, new ArrayList<>(list));
    }

    public void update(Trainee bean) {
        LOG.debug("update: " + bean);

        Trainee trainee = getOne(bean.getId());
        trainee.setUserId(bean.getUserId());
        trainee.setAddress(bean.getAddress());
        trainee.setBirthdate(bean.getBirthdate());
    }

    public void delete(Integer id) {
        LOG.debug("delete: " + id);

        List<Trainee> list = getAll();

        list.removeIf(t -> t.getId() == id);
        storageBean.setList(Table.TRAINEES, new ArrayList<>(list));
    }

}
