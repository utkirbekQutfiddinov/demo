package uz.utkirbek.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.impl.TrainingRepositoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingRepositoryImpl trainingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainingSuccess() {
        Trainer trainer = new Trainer();
        trainer.setId(1);
        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.of(trainer));

        Trainee trainee = new Trainee();
        trainee.setId(2);
        when(traineeRepository.findByUsername("traineeUsername")).thenReturn(Optional.of(trainee));

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainerUsername("trainerUsername");
        trainingDto.setTraineeUsername("traineeUsername");
        trainingDto.setDate(new Date());
        trainingDto.setDuration(60);
        trainingDto.setName("TrainingName");

        when(entityManager.merge(any(Training.class))).thenReturn(new Training());

        Optional<Training> result = trainingRepository.create(trainingDto);

        assertTrue(result.isPresent());
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testCreateTrainingException() {
        Trainer trainer = new Trainer();
        trainer.setId(1);
        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.of(trainer));

        Trainee trainee = new Trainee();
        trainee.setId(2);
        when(traineeRepository.findByUsername("traineeUsername")).thenReturn(Optional.of(trainee));

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainerUsername("trainerUsername");
        trainingDto.setTraineeUsername("traineeUsername");
        trainingDto.setDate(new Date());
        trainingDto.setDuration(60);
        trainingDto.setName("TrainingName");

        when(entityManager.merge(any(Training.class))).thenReturn(new Training());

        Optional<Training> result = trainingRepository.create(null);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateTrainingTrainerNotFound() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.empty());

        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainerUsername("trainerUsername");

        Optional<Training> result = trainingRepository.create(trainingDto);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateTrainingTraineeNotFound() {
        Trainer trainer = new Trainer();
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.of(trainer));
        when(traineeRepository.findByUsername("traineeUsername")).thenReturn(Optional.empty());

        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setTrainerUsername("trainerUsername");
        trainingDto.setTraineeUsername("traineeUsername");

        Optional<Training> result = trainingRepository.create(trainingDto);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindByIdTrainingFound() {
        Training training = new Training();
        training.setTrainingDate(new Date());
        training.setTrainee(null);
        training.setTrainer(null);
        training.setName(null);
        training.setDuration(null);

        when(entityManager.find(Training.class, 1)).thenReturn(training);

        Optional<Training> result = trainingRepository.findById(1);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdTrainingNotFound() {
        when(entityManager.find(Training.class, 1)).thenReturn(null);

        Optional<Training> result = trainingRepository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);

        List<Training> result = trainingRepository.findAll();

        assertNotNull(result);
    }

    @Test
    void testUpdateTrainer_Success() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(entityManager.find(Training.class, 1)).thenReturn(new Training());
        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.of(new Trainer()));

        Optional<Boolean> result = trainingRepository.updateTrainer(1, "trainerUsername");

        assertTrue(result.isPresent());
        assertTrue(result.get());
    }

    @Test
    void testUpdateTrainerTrainingNotFound() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(entityManager.find(Training.class, 1)).thenReturn(null);

        Optional<Boolean> result = trainingRepository.updateTrainer(1, "trainerUsername");

        assertFalse(result.isPresent());
    }


    @Test
    void testUpdateTrainerTrainerNotFound() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(entityManager.find(Training.class, 1)).thenReturn(new Training());
        when(trainerRepository.findByUsername("trainerUsername")).thenReturn(Optional.empty());

        Optional<Boolean> result = trainingRepository.updateTrainer(1, "trainerUsername");

        assertFalse(result.isPresent());
    }


    @Test
    void testGetByCriteria() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);

        Root trainingRoot = mock(Root.class);
        when(criteriaQuery.from(Training.class)).thenReturn(trainingRoot);

        Join trainerJoin = mock(Join.class);
        when(trainingRoot.join("trainer", JoinType.LEFT)).thenReturn(trainerJoin);

        Join trainingTypeJoin = mock(Join.class);
        when(trainerJoin.join("trainingType", JoinType.LEFT)).thenReturn(trainingTypeJoin);

        Join userJoin = mock(Join.class);
        when(trainingRoot.join("user", JoinType.LEFT)).thenReturn(userJoin);

        Join traineeJoin = mock(Join.class);
        when(trainingRoot.join("trainee", JoinType.LEFT)).thenReturn(traineeJoin);

        Join traineeUserJoin = mock(Join.class);
        when(traineeJoin.join("trainer", JoinType.LEFT)).thenReturn(traineeUserJoin);

        TypedQuery typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        when(typedQuery.getResultList()).thenReturn(Collections.singletonList(new Training()));


        when(trainerRepository.findByTrainingId(anyInt())).thenReturn(new Trainer());
        when(traineeRepository.findByTrainingId(anyInt())).thenReturn(new Trainee());

        Query query = mock(Query.class);
        when(entityManager.createQuery((CriteriaDelete) any())).thenReturn(query);

        List<TrainingResponse> result = trainingRepository.getByCriteria(new TrainingFiltersDto());

        assertNotNull(result);
    }

    @Test
    void testGetByCriteriaNoResults() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);

        Root trainingRoot = mock(Root.class);
        when(criteriaQuery.from(Training.class)).thenReturn(trainingRoot);

        Join trainerJoin = mock(Join.class);
        when(trainingRoot.join("trainer", JoinType.LEFT)).thenReturn(trainerJoin);

        Join trainingTypeJoin = mock(Join.class);
        when(trainerJoin.join("trainingType", JoinType.LEFT)).thenReturn(trainingTypeJoin);

        Join userJoin = mock(Join.class);
        when(trainingRoot.join("user", JoinType.LEFT)).thenReturn(userJoin);

        Join traineeJoin = mock(Join.class);
        when(trainingRoot.join("trainee", JoinType.LEFT)).thenReturn(traineeJoin);

        Join traineeUserJoin = mock(Join.class);
        when(traineeJoin.join("trainer", JoinType.LEFT)).thenReturn(traineeUserJoin);

        TypedQuery typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(trainerRepository.findByTrainingId(anyInt())).thenReturn(null);
        when(traineeRepository.findByTrainingId(anyInt())).thenReturn(null);

        Query query = mock(Query.class);
        when(entityManager.createQuery((CriteriaDelete) any())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        List<TrainingResponse> result = trainingRepository.getByCriteria(new TrainingFiltersDto());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetByCriteriaException() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);

        Root trainingRoot = mock(Root.class);
        when(criteriaQuery.from(Training.class)).thenReturn(trainingRoot);

        Join trainerJoin = mock(Join.class);
        when(trainingRoot.join("trainer", JoinType.LEFT)).thenReturn(trainerJoin);

        Join trainingTypeJoin = mock(Join.class);
        when(trainerJoin.join("trainingType", JoinType.LEFT)).thenReturn(trainingTypeJoin);

        Join userJoin = mock(Join.class);
        when(trainingRoot.join("user", JoinType.LEFT)).thenReturn(userJoin);

        Join traineeJoin = mock(Join.class);
        when(trainingRoot.join("trainee", JoinType.LEFT)).thenReturn(traineeJoin);

        Join traineeUserJoin = mock(Join.class);
        when(traineeJoin.join("trainer", JoinType.LEFT)).thenReturn(traineeUserJoin);

        TypedQuery typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(trainerRepository.findByTrainingId(anyInt())).thenReturn(new Trainer());
        when(traineeRepository.findByTrainingId(anyInt())).thenReturn(new Trainee());

        Query query = mock(Query.class);
        when(entityManager.createQuery((CriteriaDelete) any())).thenReturn(query);
        when(query.getResultList()).thenThrow(new NoResultException());

        List<TrainingResponse> result = trainingRepository.getByCriteria(new TrainingFiltersDto());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
