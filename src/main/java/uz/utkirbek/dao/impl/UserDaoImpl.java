package uz.utkirbek.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.UserDao;
import uz.utkirbek.model.User;
import uz.utkirbek.storage.StorageBean;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
    private final StorageBean storageBean;

    public UserDaoImpl(StorageBean storageBean) {
        this.storageBean = storageBean;
    }

    public List<User> getAll() {
        LOG.info("getAll: ");

        return storageBean.getList("users");
    }

    public User getOne(Integer id) {
        LOG.info("getOne: "+id);

        for(User user: getAll()){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    public void add(User bean) throws Exception {
        LOG.info("add: "+bean);

        List<User> list=getAll();
        list.add(bean);
        storageBean.setList("users",list);
    }

    public void update(User bean) throws Exception {
        LOG.info("update: "+bean);

        User user=getOne(bean.getId());
        user.setActive(bean.getActive());
        user.setUsername(bean.getUsername());
        user.setPassword(bean.getPassword());
        user.setFirstname(bean.getFirstname());
        user.setLastname(bean.getLastname());

    }

    public void delete(Integer id) throws Exception {
        LOG.info("delete: "+id);

        List<User> list=getAll();

        list.removeIf(t -> t.getId().equals(id));
    }
}
