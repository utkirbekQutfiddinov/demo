package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.User;

import java.util.List;

import static org.junit.Assert.*;

public class UserDaoImpl {
    private BaseDeleteDao<User> userDao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.userDao=context.getBean(uz.utkirbek.dao.impl.UserDaoImpl.class);
    }

    @Test
    public void testGetAll() {
        List<User> users = userDao.getAll();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testAddAndGetOne() {
        final int userId=100;
        User userToAdd = new User(userId, "Utkirbek", "Qutfiddinov");
        userDao.add(userToAdd);

        User retrievedUser = userDao.getOne(userId);
        assertNotNull(retrievedUser);
        assertEquals(userToAdd.getFirstname(), retrievedUser.getFirstname());
    }

    @Test
    public void testGetOneWhichIsNotExists(){
        User retrievedUser = userDao.getOne(Integer.MAX_VALUE);
        assertNull(retrievedUser);
    }

    @Test
    public void testUpdate() {
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
    public void testDelete() {
        final int userId=100;
        User userToDelete = new User(userId, "Utkirbek", "Qutfiddinov");
        userDao.add(userToDelete);

        List<User> initialUsers = userDao.getAll();

        userDao.delete(userId);

        List<User> remainingUsers = userDao.getAll();
        assertEquals(initialUsers.size(), remainingUsers.size()+1);
    }
}

