package uz.utkirbek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.utkirbek.dao.BaseDao;
import uz.utkirbek.model.User;
import uz.utkirbek.service.BaseService;

import java.util.List;

@Service
public class UserServiceImpl implements BaseService<User> {
    static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Qualifier(value = "userDaoImpl")
    private BaseDao<User> dao;

    public List<User> getAll() {
        return dao.getAll();
    }

    public User getOne(Integer id) {
        return dao.getOne(id);
    }

    public void add(User bean) {

        bean.setUsername(generateUsername(bean.getFirstname(), bean.getLastname()));
        bean.setPassword(generatePassword());
        bean.setActive(true);

        try {
            dao.add(bean);
        } catch (Exception e) {
            LOG.debug("Error on adding: "+e.getMessage());
        }
    }

    public void update(User bean) {
        try{
        dao.update(bean);
        } catch (Exception e) {
            LOG.debug("Error on updating"+e.getMessage());
        }
    }

    public void delete(Integer id) {
        try{
        dao.delete(id);
        } catch (Exception e) {
            LOG.debug("Error on deleting"+e.getMessage());
        }
    }

    public User getByUsername(String username) {
        return getAll().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    private String generateUsername(String firstname, String lastname) {
        String baseUsername = firstname + lastname;
        String username = baseUsername;

        int serialNumber = 1;
        while (usernameExists(username)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        return username;
    }

    private boolean usernameExists(String username) {
        User existingUser = this.getByUsername(username);
        return existingUser != null;
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
