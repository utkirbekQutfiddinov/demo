package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.model.User;
import uz.utkirbek.repository.TrainerRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.TrainerServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll() {
        List<Trainer> trainerList = mock(List.class);
        when(trainerRepository.findAll()).thenReturn(trainerList);

        List<Trainer> result = trainerService.getAll();

        assertEquals(trainerList, result);
    }

    @Test
    public void getOneTrainerFound() {
        Trainer trainer = new Trainer();
        when(trainerRepository.findById(1)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getOne(1);

        assertEquals(trainer, result);
    }

    @Test
    public void getOneTrainerNotFound() {
        when(trainerRepository.findById(1)).thenReturn(Optional.empty());

        assertNull(trainerService.getOne(1));
    }

    @Test
    public void addTrainerCreated() {
        Trainer trainer = new Trainer();
        when(trainerRepository.create(trainer)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.add(trainer);

        assertEquals(trainer, result);
    }

    @Test
    public void addTrainerCreationFailed() {
        Trainer trainer = new Trainer();
        when(trainerRepository.create(trainer)).thenReturn(Optional.empty());

        assertNull(trainerService.add(trainer));
    }

    @Test
    public void updateTrainer() {
        Trainer trainer = new Trainer();
        when(trainerRepository.update(trainer)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.update(trainer);

        assertEquals(trainer, result);
    }

    @Test
    public void getByUserName() {
        String username = "testUser";
        Trainer trainer = new Trainer();
        when(trainerRepository.findByUsername(username)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getByUserName(username);

        assertEquals(trainer, result);
    }

    @Test
    public void changePasswordSuccess() {
        int trainerId = 1;
        String password = "newPassword";
        Trainer trainer = new Trainer();
        trainer.setUser(new User(100));
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changePassword(trainer.getUser().getId(), password)).thenReturn(Optional.of(true));

        Boolean isChanged = trainerService.changePassword(trainerId, password);

        assertEquals(true, isChanged);
    }

    @Test
    public void changePasswordTrainerNotFound() {
        int trainerId = 1;
        String password = "newPassword";
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

        Boolean isChanged = trainerService.changePassword(trainerId, password);

        assertEquals(false, isChanged);
    }

    @Test
    public void changePasswordFailure() {
        int trainerId = 1;
        String password = "newPassword";
        Trainer trainer = new Trainer();
        trainer.setUser(new User(100));
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changePassword(trainer.getUser().getId(), password)).thenReturn(Optional.empty());

        Boolean isChanged = trainerService.changePassword(trainerId, password);

        assertEquals(false, isChanged);
    }

    @Test
    public void changeStatusSuccess() {
        int trainerId = 1;
        Trainer trainer = new Trainer();
        trainer.setUser(new User(100));
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changeStatus(trainer.getUser().getId())).thenReturn(Optional.of(true));

        Boolean isChanged = trainerService.changeStatus(trainerId);

        assertEquals(true, isChanged);
    }

    @Test
    public void changeStatusTrainerNotFound() {
        int trainerId = 1;
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

        Boolean isChanged = trainerService.changeStatus(trainerId);

        assertEquals(false, isChanged);
    }

    @Test
    public void changeStatusFailure() {
        int trainerId = 1;
        Trainer trainer = new Trainer();
        trainer.setUser(new User(100));
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changeStatus(trainer.getUser().getId())).thenReturn(Optional.empty());

        Boolean isUpdated = trainerService.changeStatus(trainerId);

        assertEquals(false, isUpdated);
    }

    @Test
    public void getNotAssignedAndActive() {
        List<Trainer> trainerList = mock(List.class);
        when(trainerRepository.getNotAssignedAndActive()).thenReturn(trainerList);

        List<Trainer> result = trainerService.getNotAssignedAndActive();

        assertEquals(trainerList, result);
    }

    @Test
    public void getTrainingsByUsernameAndCriteria() {
        String username = "testUser";
        List<Training> trainingList = mock(List.class);
        when(trainingRepository.getByUsernameAndCriteria(username)).thenReturn(trainingList);

        List<Training> result = trainerService.getTrainingsByUsernameAndCriteria(username);

        assertEquals(trainingList, result);
    }
}

