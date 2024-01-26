package uz.utkirbek.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.User;
import uz.utkirbek.repository.impl.TraineeRepositoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TraineeRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private TraineeRepositoryImpl traineeRepository;
    @Mock
    private Query nativeQuery;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    void create() {
        Trainee trainee = new Trainee();
        trainee.setBirthdate(new Date());
        User user = new User();
        user.setId(100);

        trainee.setUser(user);
        trainee.setTrainings(new ArrayList<>());

        when(entityManager.merge(any(Trainee.class))).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.create(trainee);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());

        verify(transaction).begin();
        verify(transaction).commit();
        verify(transaction, never()).rollback();
    }

    @Test
    void createWithoutUserId() {
        Trainee trainee = new Trainee();

        Optional<Trainee> result = traineeRepository.create(trainee);

        assertFalse(result.isPresent());
    }


    @Test
    void findById() {
        Trainee trainee = new Trainee();
        trainee.setId(1);

        when(entityManager.find(Trainee.class, 1)).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.findById(1);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void findById_NotFound() {
        when(entityManager.find(Trainee.class, 1)).thenReturn(null);

        Optional<Trainee> result = traineeRepository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll() {
        String sql = "select u.* from trainees u";
        Query query = mock(Query.class);

        when(entityManager.createNativeQuery(sql)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Trainee> result = traineeRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateWithNullUserId() {
        Trainee trainee = new Trainee();

        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<Trainee> result = traineeRepository.update(trainee);

        assertFalse(result.isPresent());
        verify(transaction).commit();
    }

    @Test
    public void updateWithValidUserId() {
        User user = new User();
        user.setId(100);

        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1);
        existingTrainee.setUser(user);

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(1);
        updatedTrainee.setUser(user);
        updatedTrainee.setAddress("Updated address");

        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.find(Trainee.class, 1)).thenReturn(existingTrainee);
        when(entityManager.merge(updatedTrainee)).thenReturn(updatedTrainee);

        Optional<Trainee> result = traineeRepository.update(updatedTrainee);

        assertTrue(result.isPresent());
        assertEquals("Updated address", result.get().getAddress());
        verify(entityManager).merge(updatedTrainee);
        verify(transaction).commit();
    }


    @Test
    public void deleteWhenEntityIsManaged() {
        Trainee managedTrainee = new Trainee();
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.contains(managedTrainee)).thenReturn(true);

        traineeRepository.delete(managedTrainee);

        verify(transaction).begin();
        verify(entityManager).remove(managedTrainee);
        verify(transaction).commit();
    }

    @Test
    public void deleteWhenEntityIsNotManaged() {
        Trainee unmanagedTrainee = new Trainee();
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.contains(unmanagedTrainee)).thenReturn(false);

        traineeRepository.delete(unmanagedTrainee);

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void findByUsername() {
        String username = "testUsername";
        Trainee expectedTrainee = new Trainee();

        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter(anyString(), any())).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(expectedTrainee);

        Optional<Trainee> result = traineeRepository.findByUsername(username);

        assertEquals(Optional.of(expectedTrainee), result);
        verify(transaction).begin();
        verify(transaction).commit();
        verify(nativeQuery).setParameter("username", username);
    }

}
