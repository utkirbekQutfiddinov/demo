package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.dao.impl.UserDaoImpl;
import uz.utkirbek.model.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDaoImplTest {
    private BaseDao<User> userDao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.userDao=context.getBean(UserDaoImpl.class);
    }

    @Test
    public void testGetAll() {
        List<User> users = userDao.getAll();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        final int userId=100;
        User userToAdd = new User(userId, "Utkirbek", "Qutfiddinov");
        userDao.add(userToAdd);

        User retrievedUser = userDao.getOne(userId);
        assertNotNull(retrievedUser);
        assertEquals(userToAdd.getFirstname(), retrievedUser.getFirstname());
    }

    @Test
    public void testUpdate() throws Exception {
        final int userId=100;
        User initialUser = new User(userId, "Utkirbek", "Qutfiddinov");
        userDao.add(initialUser);

        User updatedUser = new User(userId, "Akbar", "Akbarov");
        userDao.update(updatedUser);

        User retrievedUser = userDao.getOne(userId);
        assertNotNull(retrievedUser);
        assertEquals(updatedUser.getFirstname(), retrievedUser.getFirstname());
        assertEquals(updatedUser.getLastname(), retrievedUser.getLastname());
    }

    @Test
    public void testDelete() throws Exception {
        final int userId=100;
        User userToDelete = new User(userId, "Utkirbek", "Qutfiddinov");
        userDao.add(userToDelete);

        List<User> initialUsers = userDao.getAll();

        userDao.delete(userId);

        List<User> remainingUsers = userDao.getAll();
        assertEquals(initialUsers.size(), remainingUsers.size()+1);
    }
}

