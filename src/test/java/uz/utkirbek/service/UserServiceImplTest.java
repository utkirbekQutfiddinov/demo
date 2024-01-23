package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.User;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private ServiceFacade serviceFacade;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
        serviceFacade = context.getBean(ServiceFacade.class);
    }

    @Test
    public void getAll() {

        for (int i = 0; i < 10; i++) {
            User newUser = new User(String.valueOf(i * 123), String.valueOf(i * 456));
            serviceFacade.addUser(newUser);
        }

        List<User> allUsers = serviceFacade.getAllUsers();

        assertEquals(13, allUsers.size());
    }

    @Test
    public void getOne() {
        final int userId = 100;
        User testUser = new User("Utkirbek", "Qutfiddinov");
        testUser.setId(userId);

        serviceFacade.addUser(testUser);

        User user = serviceFacade.getUser(userId);

        assertEquals(user.getFirstname(), testUser.getFirstname());
    }

    @Test
    public void add() {
        final int userId = 100;

        User testUser = new User();
        testUser.setFirstname("Utkirbek");
        testUser.setLastname("Qutfiddinov");
        testUser.setId(userId);
        serviceFacade.addUser(testUser);

        User user = serviceFacade.getUser(userId);

        assertNotNull(user);
        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());

    }

    @Test
    public void update() {

        final int userId = 100;

        User testUser = new User("Utkirbek","Qutfiddinov");
        testUser.setId(userId);

        serviceFacade.addUser(testUser);

        testUser.setFirstname("newFirstName");

        serviceFacade.updateUser(testUser);

        assertEquals("newFirstName", serviceFacade.getUser(userId).getFirstname());
    }

    @Test
    public void delete() {
        final int userId=100;
        User testUser = new User("Utkirbek","Qutfiddinov");
        testUser.setId(userId);

        serviceFacade.addUser(testUser);

        User user1=serviceFacade.getUser(userId);
        assertNotNull(user1);

        serviceFacade.deleteUser(userId);
        User user2=serviceFacade.getUser(userId);
        assertNull(user2);
    }

}