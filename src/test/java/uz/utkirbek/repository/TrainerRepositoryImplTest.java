package uz.utkirbek.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.model.User;
import uz.utkirbek.repository.impl.TrainerRepositoryImpl;
import uz.utkirbek.repository.impl.UserRepositoryImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainerRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private TrainerRepositoryImpl trainerRepository;
    @InjectMocks
    private UserRepositoryImpl userRepository;
    @Mock
    private Query nativeQuery;

    @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);

        when(entityManager.getTransaction()).thenReturn(transaction);

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);

        when(query.getResultList()).thenReturn(Collections.emptyList());
    }

    @AfterEach
    void tearDown() {
        verify(transaction).begin();
        verify(transaction).commit();
        verify(transaction, never()).rollback();
    }


    @Test
    void testCreate() {

        User user=new User();
        user.setId(1);
        user.setFirstname("text");
        user.setLastname("text");
        user.setUsername("text");
        user.setPassword("text");

        when(entityManager.merge(user)).thenReturn(user);

        Trainer trainer = new Trainer();
        trainer.setUserId(user.getId());
        trainer.setUser(user);
        trainer.setTrainingType(new TrainingType());
        trainer.setSpecialization(1);

        Optional<Trainer> result = trainerRepository.create(trainer);

        assertTrue(result.isPresent());
        assertEquals("text", result.get().getUser().getFirstname());

        verify(entityManager).persist(trainer);
    }

    @Test
    void testCreate_Failure() {
        Trainer trainer = new Trainer();

        Optional<Trainer> result = trainerRepository.create(trainer);

        assertFalse(result.isPresent());

        verify(entityManager, never()).persist(trainer);
        verify(entityManager, never()).merge(trainer);
    }

    @Test
    void testReadOne() {
        Trainer trainer = new Trainer();
        trainer.setId(1);

        when(entityManager.find(Trainer.class, 1)).thenReturn(trainer);

        Optional<Trainer> result = trainerRepository.readOne(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());

        verify(entityManager).find(Trainer.class, 1);
    }

    @Test
    void testReadOne_NotFound() {
        when(entityManager.find(Trainer.class, 1)).thenReturn(null);

        Optional<Trainer> result = trainerRepository.readOne(1);

        assertFalse(result.isPresent());

        verify(entityManager).find(Trainer.class, 1);
    }

    @Test
    void testReadAll() {
        String sql = "select u.* from trainers u";
        List<Object[]> expectedResult = Arrays.asList(new Object[]{1L, "Trainer1"}, new Object[]{2L, "Trainer2"});

        when(entityManager.createNativeQuery(sql)).thenReturn(nativeQuery);
        when(nativeQuery.getResultList()).thenReturn(expectedResult);

        List<Trainer> result = trainerRepository.readAll();

        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateTrainer() {
        User user=new User();

        Trainer existingTrainer = new Trainer();
        existingTrainer.setId(1);
        existingTrainer.setUserId(100);
        existingTrainer.setUser(user);

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(1);
        updatedTrainer.setUserId(200);
        updatedTrainer.setUser(user);

        when(entityManager.find(Trainer.class, 1)).thenReturn(existingTrainer);
        when(entityManager.merge(updatedTrainer)).thenReturn(updatedTrainer);

        Optional<Trainer> result = trainerRepository.update(updatedTrainer);

        assertTrue(result.isPresent());
        assertEquals(updatedTrainer.getUserId(), result.get().getUserId());

        verify(entityManager).merge(updatedTrainer);
        verify(transaction).commit();
    }

    @Test
    void testUpdateTrainer_NotFound() {
        when(entityManager.find(Trainer.class, 1)).thenReturn(null);

        Optional<Trainer> result = trainerRepository.update(new Trainer());

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByUsername() {
        String username = "testUsername";
        Trainer expectedTrainer = new Trainer();

        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter(anyString(), any())).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(expectedTrainer);

        Optional<Trainer> result = trainerRepository.findByUsername(username);

        assertEquals(Optional.of(expectedTrainer), result);
        verify(transaction).begin();
        verify(transaction).commit();
        verify(nativeQuery).setParameter("username", username);
    }

    @Test
    void testGetNotAssignedAndActive() {
        String sql = "select t.* from trainers t left join users u on u.id=t.user_id " +
                "where count(select * from trainings t1 where t1.trainer_id=t.id)=0 " +
                "and u.is_active=true";

        List<Trainer> expectedResults = Collections.emptyList();

        when(entityManager.createNativeQuery(sql)).thenReturn(nativeQuery);
        when(nativeQuery.getResultList()).thenReturn(expectedResults);

        List<Trainer> result = trainerRepository.getNotAssignedAndActive();

        assertEquals(expectedResults, result);
    }
}

