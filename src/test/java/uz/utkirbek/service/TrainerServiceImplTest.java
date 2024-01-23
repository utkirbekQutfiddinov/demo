package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
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
        when(trainerRepository.readAll()).thenReturn(trainerList);

        List<Trainer> result = trainerService.getAll();

        assertEquals(trainerList, result);
    }

    @Test
    public void getOneTrainerFound() {
        Trainer trainer = new Trainer();
        when(trainerRepository.readOne(1)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getOne(1);

        assertEquals(trainer, result);
    }

    @Test
    public void getOneTrainerNotFound() {
        when(trainerRepository.readOne(1)).thenReturn(Optional.empty());

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
        when(trainerRepository.readOne(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changePassword(trainer.getUserId(), password)).thenReturn(Optional.of(true));

        Boolean result = trainerService.changePassword(trainerId, password);

        assertEquals(true, result);
    }

    @Test
    public void changePasswordTrainerNotFound() {
        int trainerId = 1;
        String password = "newPassword";
        when(trainerRepository.readOne(trainerId)).thenReturn(Optional.empty());

        Boolean result = trainerService.changePassword(trainerId, password);

        assertEquals(false, result);
    }

    @Test
    public void changePasswordFailure() {
        int trainerId = 1;
        String password = "newPassword";
        Trainer trainer = new Trainer();
        when(trainerRepository.readOne(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changePassword(trainer.getUserId(), password)).thenReturn(Optional.empty());

        Boolean result = trainerService.changePassword(trainerId, password);

        assertEquals(false, result);
    }

    @Test
    public void changeStatusSuccess() {
        int trainerId = 1;
        Trainer trainer = new Trainer();
        when(trainerRepository.readOne(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changeStatus(trainer.getUserId())).thenReturn(Optional.of(true));

        Boolean result = trainerService.changeStatus(trainerId);

        assertEquals(true, result);
    }

    @Test
    public void changeStatusTrainerNotFound() {
        int trainerId = 1;
        when(trainerRepository.readOne(trainerId)).thenReturn(Optional.empty());

        Boolean result = trainerService.changeStatus(trainerId);

        assertEquals(false, result);
    }

    @Test
    public void changeStatusFailure() {
        int trainerId = 1;
        Trainer trainer = new Trainer();
        when(trainerRepository.readOne(trainerId)).thenReturn(Optional.of(trainer));
        when(userRepository.changeStatus(trainer.getUserId())).thenReturn(Optional.empty());

        Boolean result = trainerService.changeStatus(trainerId);

        assertEquals(false, result);
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

