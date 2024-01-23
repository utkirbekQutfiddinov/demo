package uz.utkirbek.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.model.User;
import uz.utkirbek.repository.impl.TraineeRepositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

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
    void testCreate() {
        Trainee trainee = new Trainee();
        trainee.setUserId(1);
        trainee.setBirthdate(new Date());
        trainee.setUser(new User());
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
    void testCreateWithoutUserId() {
        Trainee trainee = new Trainee();

        Optional<Trainee> result = traineeRepository.create(trainee);

        assertFalse(result.isPresent());
    }


    @Test
    void testReadOne() {
        Trainee trainee = new Trainee();
        trainee.setId(1);

        when(entityManager.find(Trainee.class, 1)).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.readOne(1);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void testReadOne_NotFound() {
        when(entityManager.find(Trainee.class, 1)).thenReturn(null);

        Optional<Trainee> result = traineeRepository.readOne(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testReadAll() {
        String sql = "select u.* from trainees u";
        Query query = mock(Query.class);

        when(entityManager.createNativeQuery(sql)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Trainee> result = traineeRepository.readAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testUpdateWithNullUserId() {
        Trainee trainee = new Trainee();

        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<Trainee> result = traineeRepository.update(trainee);

        assertFalse(result.isPresent());
        verify(transaction).commit();
    }

    @Test
    public void testUpdateWithValidUserId() {
        Trainee existingTrainee = new Trainee();
        existingTrainee.setId(1);
        existingTrainee.setUserId(1);

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(1);
        updatedTrainee.setUserId(1);
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
    public void testDeleteWhenEntityIsManaged() {
        Trainee managedTrainee = new Trainee();
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.contains(managedTrainee)).thenReturn(true);

        traineeRepository.delete(managedTrainee);

        verify(transaction).begin();
        verify(entityManager).remove(managedTrainee);
        verify(transaction).commit();
    }

    @Test
    public void testDeleteWhenEntityIsNotManaged() {
        Trainee unmanagedTrainee = new Trainee();
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.contains(unmanagedTrainee)).thenReturn(false);

        traineeRepository.delete(unmanagedTrainee);

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testFindByUsername() {
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

