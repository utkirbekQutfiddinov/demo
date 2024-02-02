package uz.utkirbek.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.impl.TraineeRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TraineeRepositoryImplTest1 {

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TraineeRepositoryImpl traineeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainee_Success() {
        String firstName = "utkirbek";
        String lastName = "qutfiddinov";
        TraineeDto traineeDto = new TraineeDto(firstName, lastName, null, null);
        User user = new User(firstName, lastName);
        when(userRepository.create(any())).thenReturn(Optional.of(user));

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<Trainee> result = traineeRepository.create(traineeDto);

        verify(transaction).begin();
        verify(transaction).commit();
        assertTrue(result.isPresent());
        assertEquals(result.get().getUser().getUsername(), firstName + lastName);
    }

    @Test
    void testCreateTrainee_Failure() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        TraineeDto traineeDto = new TraineeDto();
        when(userRepository.create(any())).thenReturn(Optional.empty());

        Optional<Trainee> result = traineeRepository.create(traineeDto);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindById_ExistingTrainee() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        int traineeId = 1;
        Trainee trainee = new Trainee();
        when(entityManager.find(eq(Trainee.class), anyInt())).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.findById(traineeId);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.orElse(null));
    }

    @Test
    void testFindById_NonExistingTrainee() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        int traineeId = 1;
        when(entityManager.find(eq(Trainee.class), anyInt())).thenReturn(null);

        Optional<Trainee> result = traineeRepository.findById(traineeId);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);

        List<Trainee> trainees = new ArrayList<>();
        when(query.getResultList()).thenReturn(trainees);

        List<Trainee> result = traineeRepository.findAll();

        assertEquals(trainees, result);
    }

    @Test
    void testUpdateTrainee_Success() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Trainee trainee = new Trainee();
        when(entityManager.merge(any())).thenReturn(trainee);

        Optional<Trainee> result = traineeRepository.update(trainee);

        assertEquals(trainee, result.orElse(null));
    }

    @Test
    void testUpdateTrainee_Failure() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        when(entityManager.merge(any())).thenReturn(null);

        Optional<Trainee> result = traineeRepository.update(new Trainee());

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteTrainee() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        traineeRepository.delete(new Trainee());

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void findByUsername_WhenUserExists_ShouldReturnTrainee() {
        String username = "testUsername";
        Trainee expectedTrainee = new Trainee();
        User user = new User();
        user.setUsername(username);
        expectedTrainee.setUser(user);

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root traineeRoot = mock(Root.class);
        Join userJoin = mock(Join.class);
        TypedQuery typedQuery = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainee.class)).thenReturn(traineeRoot);
        when(traineeRoot.join("user")).thenReturn(userJoin);
        when(criteriaQuery.where()).thenReturn(criteriaQuery);
        when(criteriaBuilder.equal(userJoin.get("username"), username)).thenReturn(mock(Predicate.class));
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expectedTrainee);

        Optional<Trainee> result = traineeRepository.findByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(expectedTrainee, result.get());

    }

    @Test
    void findByUsername_WhenNoResult_ShouldReturnEmptyOptional() {
        String username = "nonExistentUsername";

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root traineeRoot = mock(Root.class);
        Join userJoin = mock(Join.class);
        TypedQuery typedQuery = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainee.class)).thenReturn(traineeRoot);
        when(traineeRoot.join("user")).thenReturn(userJoin);
        when(criteriaQuery.where()).thenReturn(criteriaQuery);
        when(criteriaBuilder.equal(userJoin.get("username"), username)).thenReturn(mock(Predicate.class));
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        Optional<Trainee> result = traineeRepository.findByUsername(username);

        assertFalse(result.isPresent());

    }

    @Test
    void findByUsername_WhenExceptionThrown_ShouldReturnEmptyOptional() {
        String username = "testUsername";

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root traineeRoot = mock(Root.class);
        Join userJoin = mock(Join.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainee.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainee.class)).thenReturn(traineeRoot);
        when(traineeRoot.join("user")).thenReturn(userJoin);
        when(criteriaQuery.where()).thenReturn(criteriaQuery);
        when(criteriaBuilder.equal(userJoin.get("username"), username)).thenReturn(mock(Predicate.class));
        when(entityManager.createQuery(criteriaQuery)).thenThrow(new RuntimeException("Test exception"));

        Optional<Trainee> result = traineeRepository.findByUsername(username);

        assertFalse(result.isPresent());

    }

    @Test
    void findByTrainingId_Success() {
        MockitoAnnotations.openMocks(this);

        Integer trainingId = 1;

        Object[] mockResult = {1, "MockAddress", "2022-01-30 12:00:00", 1};

        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(mockResult);

        User mockUser = new User();
        mockUser.setId(1);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mockUser));

        Trainee result = traineeRepository.findByTrainingId(trainingId);

        assertNull(result);
    }

}
