package uz.utkirbek.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.impl.TrainingTypeRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingTypeRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainingTypeRepositoryImpl trainingTypeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTrainingTypeSuccess() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        TrainingType trainingType = new TrainingType();
        when(entityManager.merge(any(TrainingType.class))).thenReturn(trainingType);

        Optional<TrainingType> result = trainingTypeRepository.create(trainingType);

        assertTrue(result.isPresent());
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testFindByIdTrainingTypeFound() {
        TrainingType trainingType = new TrainingType();
        when(entityManager.find(TrainingType.class, 1)).thenReturn(trainingType);

        Optional<TrainingType> result = trainingTypeRepository.findById(1);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindByIdTrainingTypeNotFound() {
        when(entityManager.find(TrainingType.class, 1)).thenReturn(null);

        Optional<TrainingType> result = trainingTypeRepository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString())).thenReturn(query);

        List<TrainingType> result = trainingTypeRepository.findAll();

        assertNotNull(result);
    }

    @Test
    void testFindByNameTrainingTypeFound() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(TrainingType.class)).thenReturn(criteriaQuery);

        Root trainingTypeRoot = mock(Root.class);
        when(criteriaQuery.from(TrainingType.class)).thenReturn(trainingTypeRoot);

        Subquery subquery = mock(Subquery.class);
        when(criteriaQuery.subquery(Long.class)).thenReturn(subquery);

        Root trainerRoot = mock(Root.class);
        when(subquery.from(Trainer.class)).thenReturn(trainerRoot);

        Join trainerUserJoin = mock(Join.class);
        when(trainerRoot.join("user", JoinType.LEFT)).thenReturn(trainerUserJoin);

        TypedQuery typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        TrainingType trainingType = new TrainingType();
        when(typedQuery.getSingleResult()).thenReturn(trainingType);

        Optional<TrainingType> result = trainingTypeRepository.findByName("TrainingType");

        assertTrue(result.isPresent());
        assertEquals(trainingType.getName(), result.get().getName());
    }

    @Test
    void testFindByNameTrainingTypeNotFound() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(TrainingType.class)).thenReturn(criteriaQuery);

        Root trainingTypeRoot = mock(Root.class);
        when(criteriaQuery.from(TrainingType.class)).thenReturn(trainingTypeRoot);

        Subquery subquery = mock(Subquery.class);
        when(criteriaQuery.subquery(Long.class)).thenReturn(subquery);

        Root trainerRoot = mock(Root.class);
        when(subquery.from(Trainer.class)).thenReturn(trainerRoot);

        Join trainerUserJoin = mock(Join.class);
        when(trainerRoot.join("user", JoinType.LEFT)).thenReturn(trainerUserJoin);

        TypedQuery typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(new NoResultException());

        Optional<TrainingType> result = trainingTypeRepository.findByName("NonExistentType");

        assertFalse(result.isPresent());
    }

    @Test
    void findByUsername_WhenUsernameExists_ReturnsTrainingType() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);

        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        when(criteriaBuilder.createQuery(TrainingType.class)).thenReturn(criteriaQuery);

        Root trainingTypeRoot = mock(Root.class);
        when(criteriaQuery.from(TrainingType.class)).thenReturn(trainingTypeRoot);

        Subquery subquery = mock(Subquery.class);
        when(criteriaQuery.subquery(Long.class)).thenReturn(subquery);


        Root trainerRoot = mock(Root.class);
        when(subquery.from(Trainer.class)).thenReturn(trainerRoot);

        Path trainerRootGet = mock(Path.class);
        when(trainerRoot.get(anyString())).thenReturn(trainerRootGet);

        Join<Trainer, User> trainerUserJoin = mock(Join.class);
        when(trainerRoot.join("user", JoinType.LEFT)).thenReturn(trainerUserJoin);


        when(subquery.select(any())).thenReturn(subquery);

        when(criteriaQuery.select(trainingTypeRoot)).thenReturn(criteriaQuery);

        TypedQuery<TrainingType> typedQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);

        when(typedQuery.getSingleResult()).thenReturn(new TrainingType());

        Optional<TrainingType> result = trainingTypeRepository.findByUsername("testUsername");

        assertTrue(result.isPresent());
        verify(entityManager, times(1)).getCriteriaBuilder();
        verify(criteriaBuilder, times(1)).createQuery(TrainingType.class);
        verify(criteriaQuery, times(1)).from(TrainingType.class);
        verify(criteriaQuery, times(1)).subquery(Long.class);
        verify(subquery, times(1)).from(Trainer.class);
        verify(trainerRoot, times(1)).join("user", JoinType.LEFT);
        verify(subquery, times(1)).select(trainerRoot.get("trainingType").get("id"));
        verify(subquery, times(1)).where(criteriaBuilder.equal(trainerUserJoin.get("username"), "testUsername"));
        verify(criteriaQuery, times(1)).select(trainingTypeRoot);
        verify(criteriaBuilder, times(1)).equal(trainingTypeRoot.get("id"), subquery);
        verify(entityManager, times(1)).createQuery(criteriaQuery);
        verify(typedQuery, times(1)).getSingleResult();
    }
}
