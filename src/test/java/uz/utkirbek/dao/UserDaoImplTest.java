package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import uz.utkirbek.dao.impl.UserDaoImpl;
import uz.utkirbek.model.User;
import uz.utkirbek.storage.StorageBean;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDaoImplTest {

    private UserDao userDao;

    @Before
    public void setUp() {
        StorageBean storageBean = new StorageBean(new HashMap<>());
        userDao = new UserDaoImpl(storageBean);
    }

    @Test
    public void testGetAll() {
        List<User> users = userDao.getAll();
        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        User userToAdd = createUser(1, "Utkirbek", "Qutfiddinov");
        userDao.add(userToAdd);

        List<User> users = userDao.getAll();
        assertEquals(1, users.size());

        User retrievedUser = userDao.getOne(1);
        assertNotNull(retrievedUser);
        assertUserEquals(userToAdd, retrievedUser);
    }

    @Test
    public void testUpdate() throws Exception {
        User initialUser = createUser(1, "Utkirbek", "Qutfiddinov");
        userDao.add(initialUser);

        User updatedUser = createUser(1, "Akbar", "Akbarov");
        userDao.update(updatedUser);

        User retrievedUser = userDao.getOne(1);
        assertNotNull(retrievedUser);
        assertUserEquals(updatedUser, retrievedUser);
    }

    @Test
    public void testDelete() throws Exception {
        User userToDelete = createUser(1, "Utkirbek", "Qutfiddinov");
        userDao.add(userToDelete);

        List<User> initialUsers = userDao.getAll();
        assertEquals(1, initialUsers.size());

        userDao.delete(1);

        List<User> remainingUsers = userDao.getAll();
        assertEquals(0, remainingUsers.size());
    }

    private User createUser(Integer id, String firstname, String lastname) {
        User user = new User();
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);

        return user;
    }

    private void assertUserEquals(User expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getActive(), actual.getActive());
    }
}

