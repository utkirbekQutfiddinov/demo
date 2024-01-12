package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.enums.TABLE;
import uz.utkirbek.model.BaseIdBean;
import uz.utkirbek.model.User;
import uz.utkirbek.storage.StorageBean;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements BaseDao<User> {
    static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
    private final StorageBean storageBean;

    public UserDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<User> getAll() {
        LOG.debug("getAll: ");
        List<? extends BaseIdBean> baseIdBeans = storageBean.getList(TABLE.USERS);

        List<User> users = new ArrayList<>();
        for (BaseIdBean baseIdBean : baseIdBeans) {
            if (baseIdBean instanceof User) {
                users.add((User) baseIdBean);
            }
        }

        return users;
    }

    public User getOne(Integer id) {
        LOG.debug("getOne: "+id);

        for(User user: getAll()){
            if(user.getId()==id){
                return user;
            }
        }

        return null;
    }

    public void add(User bean) throws Exception {
        LOG.debug(String.format("add: %s",bean));

        List<User> list=getAll();
        bean.setId(bean.getId()==0?list.size():bean.getId());
        list.add(bean);
        storageBean.setList(TABLE.USERS,new ArrayList<>(list));
    }

    public void update(User bean) throws Exception {
        LOG.debug("update: "+bean);

        User user=getOne(bean.getId());
        user.setActive(bean.getActive());
        user.setUsername(bean.getUsername());
        user.setPassword(bean.getPassword());
        user.setFirstname(bean.getFirstname());
        user.setLastname(bean.getLastname());

    }

    public void delete(Integer id) throws Exception {
        LOG.debug("delete: "+id);

        List<User> list=getAll();

        list.removeIf(t -> t.getId()==id);
        storageBean.setList(TABLE.USERS,new ArrayList<>(list));
    }
}
