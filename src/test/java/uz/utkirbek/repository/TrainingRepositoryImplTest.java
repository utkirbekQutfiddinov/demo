package uz.utkirbek.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.repository.impl.TrainingRepositoryImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingRepositoryImplTest {

    private EntityManager entityManager;
    private TrainingRepository trainingRepository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        trainingRepository = new TrainingRepositoryImpl(entityManager);
    }

    @Test
    void createTraining() throws ParseException {
        Training training = new Training();
        training.setName("Test Training");
        training.setTrainingDate(parseToDate("2022-01-01"));
        training.setDuration(2 * 3600 * 1000);

        training.setTrainer(new Trainer());

        training.setTraineeId(1);
        training.setTrainee(new Trainee());

        training.setTrainingTypeId(1);
        training.setTrainingType(new TrainingType());

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<Training> result = trainingRepository.create(training);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());

        if (training.getId() == 0) {
            verify(entityManager).persist(training);
        } else {
            verify(entityManager).merge(training);
        }

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void createTrainingWithNameNull() {
        Training training = new Training();

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<Training> result = trainingRepository.create(training);

        assertFalse(result.isPresent());

        verify(entityManager, never()).persist(training);
        verify(entityManager, never()).merge(training);
    }

    @Test
    void readOneTraining() throws ParseException {
        int trainingId = 1;
        Training expectedTraining = new Training();
        expectedTraining.setName("Test Training");
        expectedTraining.setTrainingDate(parseToDate("2022-01-01"));
        expectedTraining.setDuration(2 * 3600 * 1000);

        expectedTraining.setTrainer(new Trainer());

        expectedTraining.setTraineeId(1);
        expectedTraining.setTrainee(new Trainee());

        expectedTraining.setTrainingTypeId(1);
        expectedTraining.setTrainingType(new TrainingType());
        expectedTraining.setId(trainingId);

        when(entityManager.find(Training.class, trainingId)).thenReturn(expectedTraining);

        Optional<Training> result = trainingRepository.findById(trainingId);

        assertTrue(result.isPresent());
        assertEquals(expectedTraining, result.get());
    }

    @Test
    void findAllTrainings() {
        String sql = "select u.* from trainings u";
        Query nativeQuery = mock(Query.class);
        List<Training> expectedTrainings = new ArrayList<>();
        expectedTrainings.add(new Training());
        expectedTrainings.add(new Training());
        when(entityManager.createNativeQuery(sql)).thenReturn(nativeQuery);
        when(nativeQuery.getResultList()).thenReturn(expectedTrainings);

        List<Training> result = trainingRepository.findAll();

        assertEquals(expectedTrainings, result);
    }

    @Test
    void trainingUpdate() {

        Training training = new Training();
        training.setId(1);
        training.setDuration(1);
        training.setName("random name");
        training.setTrainingDate(new Date());


        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<Training> result = trainingRepository.create(training);

        assertFalse(result.isPresent());

        verify(entityManager, never()).persist(training);
        verify(transaction).begin();
        verify(transaction).rollback();
    }

    @Test
    void updateTrainer() {
        int trainingId = 1;
        Trainer trainer = new Trainer();
        trainer.setId(1);

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.find(Training.class, trainingId)).thenReturn(new Training());

        Optional<Boolean> isUpdated = trainingRepository.updateTrainer(trainingId, trainer);

        assertTrue(isUpdated.isPresent());
        assertTrue(isUpdated.get());

        verify(entityManager).flush();
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void updateTrainerNotFound() {
        int trainingId = 1;
        Trainer trainer = new Trainer();
        trainer.setId(1);

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.find(Training.class, trainingId)).thenReturn(null);

        Optional<Boolean> isUpdated = trainingRepository.updateTrainer(trainingId, trainer);

        assertFalse(isUpdated.isPresent());

        verify(transaction).begin();
        verify(transaction).rollback();
    }

    private Date parseToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }

    @Test
    void getByUsernameAndCriteria() {
        String username = "testUsername";


        TypedQuery<Training> typedQuery = mock(TypedQuery.class);
        Predicate mockedPredicate = mock(Predicate.class);

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Training> criteriaQuery = mock(CriteriaQuery.class);
        Root<Training> root = mock(Root.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);

        when(entityManager.createQuery(any(CriteriaQuery.class))).thenReturn(typedQuery);
        when(criteriaQuery.select(any(Selection.class))).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(criteriaBuilder.equal(root.get("username"), username)).thenReturn(mockedPredicate);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());

        List<Training> result = trainingRepository.getByUsernameAndCriteria(username);

        assertEquals(Collections.emptyList(), result);
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Training.class);
        verify(criteriaQuery).from(Training.class);
        verify(criteriaBuilder).equal(root.get("username"), username);
        verify(entityManager).createQuery(criteriaQuery);
        verify(typedQuery).getResultList();
    }

}

