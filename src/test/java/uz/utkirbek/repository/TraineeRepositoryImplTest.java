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
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.repository.impl.TraineeRepositoryImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TraineeRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;
    @Mock
    private UserRepository userRepository;

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

        Optional<Trainee> result = traineeRepository.findByUsername(username);

        assertFalse(result.isPresent());
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
    void getNotAssignedActiveTrainers_Success() {

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);


        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(TraineeTrainerResponse.class)).thenReturn(criteriaQuery);

        Root trainerRoot = mock(Root.class);
        when(criteriaQuery.from(Trainer.class)).thenReturn(trainerRoot);

        Join userJoin = mock(Join.class);
        when(trainerRoot.join("user", JoinType.LEFT)).thenReturn(userJoin);

        Subquery subquery = mock(Subquery.class);
        when(criteriaQuery.subquery(Long.class)).thenReturn(subquery);

        Root trainingRoot = mock(Root.class);
        when(subquery.from(Training.class)).thenReturn(trainingRoot);

        Join traineeJoin = mock(Join.class);
        when(trainingRoot.join("trainee", JoinType.LEFT)).thenReturn(traineeJoin);

        Join traineeUserJoin = mock(Join.class);
        when(traineeJoin.join("user", JoinType.LEFT)).thenReturn(traineeUserJoin);

        TypedQuery typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        Path path=mock(Path.class);
        when(trainingRoot.get(anyString())).thenReturn(path);

        Subquery selectedSubquery=mock(Subquery.class);
        when(subquery.select(any())).thenReturn(selectedSubquery);

        Path trainerRootGetPath=mock(Path.class);
        when(trainerRoot.get(anyString())).thenReturn(trainerRootGetPath);

        CriteriaQuery selectedCriteriaQuery=mock(CriteriaQuery.class);
        when(criteriaQuery.select(any())).thenReturn(selectedCriteriaQuery);

        List<TraineeTrainerResponse> expectedResult = new ArrayList<>();
        when(typedQuery.getResultList()).thenReturn(expectedResult);

        List<TraineeTrainerResponse> result = traineeRepository.getNotAssignedActiveTrainers("testUsername");

        assertEquals(expectedResult, result);

        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(TraineeTrainerResponse.class);
        verify(criteriaQuery).from(Trainer.class);
        verify(trainerRoot).join("user", JoinType.LEFT);
        verify(criteriaQuery).subquery(Long.class);
        verify(subquery).from(Training.class);
        verify(trainingRoot).join("trainee", JoinType.LEFT);
        verify(traineeJoin).join("user", JoinType.LEFT);
        verify(entityManager).createQuery(criteriaQuery);
    }

    @Test
    void findByTrainingId_NullResult() {
        MockitoAnnotations.openMocks(this);

        Query nativeQuery = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter(anyString(), any())).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(null);

        Trainee result = traineeRepository.findByTrainingId(1);

        assertNull(result);
    }


}

