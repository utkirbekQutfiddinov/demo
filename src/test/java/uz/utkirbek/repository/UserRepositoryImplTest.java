package uz.utkirbek.repository;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserSuccess() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        User user = new User();
        when(entityManager.merge(any(User.class))).thenReturn(user);

        Optional<User> result = userRepository.create(user);

        assertTrue(result.isPresent());
        verify(transaction).begin();
        verify(transaction).commit();
    }


    @Test
    void testFindByIdUserFound() {
        User user = new User();
        when(entityManager.find(User.class, 1)).thenReturn(user);

        Optional<User> result = userRepository.findById(1);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdUserNotFound() {
        when(entityManager.find(User.class, 1)).thenReturn(null);

        Optional<User> result = userRepository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);

        List<User> result = userRepository.findAll();

        assertNotNull(result);
    }

    @Test
    void testUpdateUser() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        Optional<User> result = userRepository.update(new User());

        assertTrue(result.isPresent());
    }

    @Test
    void testDeleteUser() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        userRepository.delete(new User());

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testFindByUsernameUserFound() {
        TypedQuery<User> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(any(), eq(User.class))).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(new User());

        Optional<User> result = userRepository.findByUsername("username");

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByUsernameUserNotFound() {
        TypedQuery<User> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(any(), eq(User.class))).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new NoResultException());

        Optional<User> result = userRepository.findByUsername("nonexistent");

        assertFalse(result.isPresent());
    }


    @Test
    void testChangeStatusSuccess() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createQuery(any(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);

        User user = mock(User.class);
        when(query.getSingleResult()).thenReturn(user);

        Optional<Boolean> result = userRepository.changeStatus("username", true);

        assertTrue(result.isPresent());
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testChangeStatusUserNotFound() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        when(entityManager.createQuery(any(), eq(User.class))).thenThrow(new NoResultException());

        Optional<Boolean> result = userRepository.changeStatus("nonexistent", true);

        assertFalse(result.isPresent());
    }

    @Test
    public void changePassword() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        when(entityManager.find(User.class, 1)).thenReturn(new User());

        Optional<Boolean> mockTest = userRepository.changePassword(1, "mockTest");
        assertTrue(mockTest.isPresent());
        assertTrue(mockTest.get());
    }


}
