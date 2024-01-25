package uz.utkirbek.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.utkirbek.model.User;
import uz.utkirbek.repository.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    private EntityManager entityManager;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        userRepository = new UserRepositoryImpl(entityManager);
    }

    @Test
    void createUser() {
        User user = new User();
        user.setFirstname("testUser");
        user.setLastname("testPassword");
        user.setUsername("testPasswrd");
        user.setPassword("testPassword");

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<User> result = userRepository.create(user);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(entityManager).persist(user);


        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void createUserWithRequiredFieldsMissing() {
        User user = new User();
        user.setFirstname("testUser");
        user.setLastname("testPassword");
        user.setUsername("testPasswrd");

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<User> result = userRepository.create(user);

        assertFalse(result.isPresent());

        verify(transaction, never()).begin();
        verify(transaction, never()).commit();
    }

    @Test
    void createUserWithNullFields() {
        User user = new User();

        Optional<User> result = userRepository.create(user);

        assertFalse(result.isPresent());

        verify(entityManager, never()).persist(user);
        verify(entityManager, never()).merge(user);
    }

    @Test
    void updateUser() {
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("existingUser");
        existingUser.setPassword("existingPassword");
        existingUser.setFirstname("existingFirstName");
        existingUser.setLastname("existingLastName");

        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("updatedPassword");
        updatedUser.setFirstname("updatedFirstName");
        updatedUser.setLastname("updatedLastName");

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.merge(updatedUser)).thenReturn(updatedUser);
        when(entityManager.find(User.class, updatedUser.getId())).thenReturn(existingUser);

        userRepository.update(updatedUser);

        verify(entityManager).merge(updatedUser);

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void readOneUser() {
        int userId = 1;
        User expectedUser = new User();
        when(entityManager.find(User.class, userId)).thenReturn(expectedUser);

        Optional<User> result = userRepository.findById(userId);

        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }

    @Test
    void readAllUsers() {
        String sql = "select u.* from users u";
        Query nativeQuery = mock(Query.class);
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        expectedUsers.add(new User());

        when(entityManager.createNativeQuery(sql)).thenReturn(nativeQuery);
        when(nativeQuery.getResultList()).thenReturn(expectedUsers);

        List<User> result = userRepository.findAll();

        assertEquals(expectedUsers, result);
    }

    @Test
    void deleteUser() {
        User userToDelete = new User();
        userToDelete.setId(1);
        userToDelete.setUsername("userToDelete");
        userToDelete.setPassword("passwordToDelete");

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.contains(userToDelete)).thenReturn(true);

        userRepository.delete(userToDelete);

        verify(entityManager).remove(userToDelete);

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void deleteNonExistingUser() {
        User userToDelete = new User();
        userToDelete.setId(1);

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.contains(userToDelete)).thenReturn(false);

        userRepository.delete(userToDelete);

        verify(entityManager, never()).remove(userToDelete);
        verify(entityManager).contains(userToDelete);

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void getByUsername() {

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        String sql = "select u.* from users u where u.username=:username";
        Query query = mock(Query.class);

        when(entityManager.createNativeQuery(sql)).thenReturn(query);

        Optional<User> result = userRepository.findByUserName("someRandomText");

        assertFalse(result.isPresent());

        verify(transaction).begin();
        verify(transaction).commit();

    }

    @Test
    void changePassword() {
        int userId = 1;
        String newPassword = "newPassword";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setPassword("oldPassword");

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.find(User.class, userId)).thenReturn(existingUser);

        Optional<Boolean> isChanged = userRepository.changePassword(userId, newPassword);

        assertTrue(isChanged.isPresent());
        assertTrue(isChanged.get());
        assertEquals(newPassword, existingUser.getPassword());

        verify(entityManager).flush();

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void changeStatus() {
        int userId = 1;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setPassword("oldPassword");
        existingUser.setIsActive(true);

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.find(User.class, userId)).thenReturn(existingUser);

        Optional<Boolean> isChanged = userRepository.changeStatus(userId);

        assertTrue(isChanged.isPresent());
        assertEquals(!existingUser.getIsActive(), isChanged.get());

        verify(entityManager).flush();

        verify(transaction).begin();
        verify(transaction).commit();
    }
}

