package uz.utkirbek.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.repository.impl.TrainingTypeRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TrainingTypeRepositoryImplTest {

    private EntityManager entityManager;
    private TrainingTypeRepository trainingTypeRepository;

    @BeforeEach
    void setUp() {
        entityManager = mock(EntityManager.class);
        trainingTypeRepository = new TrainingTypeRepositoryImpl(entityManager);
    }

    @Test
    void createTrainingType() {
        TrainingType trainingType = new TrainingType();
        trainingType.setName("Test Training Type");

        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        Optional<TrainingType> result = trainingTypeRepository.create(trainingType);

        assertTrue(result.isPresent());
        assertEquals(trainingType, result.get());

        if (trainingType.getId() == 0) {
            verify(entityManager).persist(trainingType);
        } else {
            verify(entityManager).merge(trainingType);
        }

        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void createTrainingTypeWithNameNull() {
        TrainingType trainingType = new TrainingType();

        Optional<TrainingType> result = trainingTypeRepository.create(trainingType);

        assertFalse(result.isPresent());

        verify(entityManager, never()).persist(trainingType);
        verify(entityManager, never()).merge(trainingType);
    }

    @Test
    void readOneTrainingType() {
        int typeId = 1;
        TrainingType expectedTrainingType = new TrainingType();
        expectedTrainingType.setId(typeId);
        expectedTrainingType.setName("Test Training Type");

        when(entityManager.find(TrainingType.class, typeId)).thenReturn(expectedTrainingType);

        Optional<TrainingType> result = trainingTypeRepository.findById(typeId);

        assertTrue(result.isPresent());
        assertEquals(expectedTrainingType, result.get());
    }

    @Test
    void readAllTrainingTypes() {
        String sql = "select u.* from training_types u";
        Query nativeQuery = mock(Query.class);
        List<TrainingType> expectedTrainingTypes = new ArrayList<>();
        expectedTrainingTypes.add(new TrainingType());
        expectedTrainingTypes.add(new TrainingType());
        when(entityManager.createNativeQuery(sql)).thenReturn(nativeQuery);
        when(nativeQuery.getResultList()).thenReturn(expectedTrainingTypes);

        List<TrainingType> result = trainingTypeRepository.findAll();

        assertEquals(expectedTrainingTypes, result);
    }
}