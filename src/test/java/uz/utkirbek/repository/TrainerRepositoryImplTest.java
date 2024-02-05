package uz.utkirbek.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.impl.TrainerRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerRepositoryImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TrainerRepositoryImpl trainerRepository;

    @Test
    void create_Success() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("John");
        trainerDto.setLastName("Doe");
        trainerDto.setSpecialization(new TrainingType("Java", 1));

        when(trainingTypeRepository.findByName(anyString())).thenReturn(Optional.of(trainerDto.getSpecialization()));
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));

        Optional<Trainer> result = trainerRepository.create(trainerDto);

        assertTrue(result.isPresent());
    }

    @Test
    void create_Fail_TrainingTypeNotFound() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName("John");
        trainerDto.setLastName("Doe");
        trainerDto.setSpecialization(new TrainingType("Java", 1));

        when(trainingTypeRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));


        Optional<Trainer> result = trainerRepository.create(trainerDto);

        assertFalse(result.isPresent());
    }

    @Test
    void findById_Success() {
        Trainer trainer = new Trainer();
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(entityManager.find(eq(Trainer.class), anyInt())).thenReturn(trainer);

        Optional<Trainer> result = trainerRepository.findById(1);

        assertTrue(result.isPresent());
    }

    @Test
    void findById_NotFound() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(entityManager.find(eq(Trainer.class), anyInt())).thenReturn(null);

        Optional<Trainer> result = trainerRepository.findById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_Success() {
        when(entityManager.getTransaction()).thenReturn(mock(EntityTransaction.class));
        when(entityManager.createNativeQuery(anyString())).thenReturn(mock(Query.class));
        when(entityManager.createNativeQuery(anyString()).getResultList()).thenReturn(mock(List.class));

        List<Trainer> result = trainerRepository.findAll();

        assertNotNull(result);
    }

    @Test
    void update_Success() {
        Trainer trainer = new Trainer();
        when(entityManager.merge(any())).thenReturn(trainer);

        Optional<Trainer> result = trainerRepository.update(new Trainer());

        assertTrue(result.isPresent());
    }

    @Test
    void findByUsername_WhenUsernameExists_ReturnsTrainer() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root trainerRoot = mock(Root.class);
        Join userJoin = mock(Join.class);
        TypedQuery typedQuery = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainer.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainer.class)).thenReturn(trainerRoot);
        when(trainerRoot.join("user", JoinType.LEFT)).thenReturn(userJoin);
        when(criteriaBuilder.equal(userJoin.get("username"), "testUsername")).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(mock(Trainer.class));

        Optional<Trainer> result = trainerRepository.findByUsername("testUsername");

        assertTrue(result.isPresent());
    }

    @Test
    void findByUsername_WhenUsernameNotExists_ReturnsEmpty() {
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery criteriaQuery = mock(CriteriaQuery.class);
        Root trainerRoot = mock(Root.class);
        Join userJoin = mock(Join.class);
        TypedQuery typedQuery = mock(TypedQuery.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Trainer.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Trainer.class)).thenReturn(trainerRoot);
        when(trainerRoot.join("user", JoinType.LEFT)).thenReturn(userJoin);
        when(criteriaBuilder.equal(userJoin.get("username"), "testUsername")).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);

        Optional<Trainer> result = trainerRepository.findByUsername("testUsername");

        assertFalse(result.isPresent());
    }

    @Test
    void findByTrainingId_WhenTrainingIdExists_ReturnsTrainer() {
        Query nativeQuery = mock(Query.class);
        when(entityManager.createNativeQuery(any())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("trainingId", 1)).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(new Object[]{1, 1, 1});

        when(trainingTypeRepository.findById(1)).thenReturn(Optional.of(new TrainingType()));
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        Trainer result = trainerRepository.findByTrainingId(1);

        assertNotNull(result);
    }

    @Test
    void findByTrainingId_WhenTrainingIdNotExists_ReturnsNull() {
        Query nativeQuery = mock(Query.class);
        when(entityManager.createNativeQuery(any())).thenReturn(nativeQuery);
        when(nativeQuery.setParameter("trainingId", 1)).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenThrow(new RuntimeException()); // Simulate an exception

        Trainer result = trainerRepository.findByTrainingId(1);

        assertNull(result);
    }

}
