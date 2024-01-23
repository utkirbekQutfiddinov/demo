package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Training;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.TraineeServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Trainee> traineeList = mock(List.class);
        when(traineeRepository.readAll()).thenReturn(traineeList);

        List<Trainee> result = traineeService.getAll();

        assertEquals(traineeList, result);
    }

    @Test
    public void testGetOneTraineeFound() {
        Trainee trainee = new Trainee();
        when(traineeRepository.readOne(1)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getOne(1);

        assertEquals(trainee, result);
    }

    @Test
    public void testGetOneTraineeNotFound() {
        when(traineeRepository.readOne(1)).thenReturn(Optional.empty());

        assertNull(traineeService.getOne(1));
    }

    @Test
    public void testAddTraineeCreated() {
        Trainee trainee = new Trainee();
        when(traineeRepository.create(trainee)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.add(trainee);

        assertEquals(trainee, result);
    }

    @Test
    public void testAddTraineeCreationFailed() {
        Trainee trainee = new Trainee();
        when(traineeRepository.create(trainee)).thenReturn(Optional.empty());

        assertNull(traineeService.add(trainee));
    }

    @Test
    public void testUpdateTrainee() {
        Trainee trainee = new Trainee();
        when(traineeRepository.update(trainee)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.update(trainee);

        assertEquals(trainee, result);
    }

    @Test
    public void testDeleteTrainee() {
        Trainee trainee = new Trainee();
        when(traineeRepository.readOne(1)).thenReturn(Optional.of(trainee));

        Boolean result = traineeService.delete(1);

        assertEquals(true, result);
        verify(traineeRepository, times(1)).delete(trainee);
    }

    @Test
    public void testDeleteTraineeNotFound() {
        when(traineeRepository.readOne(1)).thenReturn(Optional.empty());

        Boolean result = traineeService.delete(1);

        assertEquals(false, result);
        verify(traineeRepository, never()).delete(any());
    }

    @Test
    public void testGetByUsername() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getByUsername(username);

        assertEquals(trainee, result);
    }

    @Test
    public void testChangePasswordSuccess() {
        int traineeId = 1;
        String password = "newPassword";
        Trainee trainee = new Trainee();
        when(traineeRepository.readOne(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changePassword(trainee.getUserId(), password)).thenReturn(Optional.of(true));

        Boolean result = traineeService.changePassword(traineeId, password);

        assertEquals(true, result);
    }

    @Test
    public void testChangePasswordTraineeNotFound() {
        int traineeId = 1;
        String password = "newPassword";
        when(traineeRepository.readOne(traineeId)).thenReturn(Optional.empty());

        Boolean result = traineeService.changePassword(traineeId, password);

        assertEquals(false, result);
    }

    @Test
    public void testChangePasswordFailure() {
        int traineeId = 1;
        String password = "newPassword";
        Trainee trainee = new Trainee();
        when(traineeRepository.readOne(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changePassword(trainee.getUserId(), password)).thenReturn(Optional.empty());

        Boolean result = traineeService.changePassword(traineeId, password);

        assertEquals(false, result);
    }

    @Test
    public void testChangeStatusSuccess() {
        int traineeId = 1;
        Trainee trainee = new Trainee();
        when(traineeRepository.readOne(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changeStatus(trainee.getUserId())).thenReturn(Optional.of(true));

        Boolean result = traineeService.changeStatus(traineeId);

        assertEquals(true, result);
    }

    @Test
    public void testChangeStatusTraineeNotFound() {
        int traineeId = 1;
        when(traineeRepository.readOne(traineeId)).thenReturn(Optional.empty());

        Boolean result = traineeService.changeStatus(traineeId);

        assertEquals(false, result);
    }

    @Test
    public void testChangeStatusFailure() {
        int traineeId = 1;
        Trainee trainee = new Trainee();
        when(traineeRepository.readOne(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changeStatus(trainee.getUserId())).thenReturn(Optional.empty());

        Boolean result = traineeService.changeStatus(traineeId);

        assertEquals(false, result);
    }

    @Test
    public void testDeleteByUsername() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));

        Boolean result = traineeService.deleteByUsername(username);

        assertEquals(true, result);
        verify(traineeRepository, times(1)).delete(trainee);
    }

    @Test
    public void testDeleteByUsernameNotFound() {
        String username = "testUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        Boolean result = traineeService.deleteByUsername(username);

        assertEquals(false, result);
        verify(traineeRepository, never()).delete(any());
    }

    @Test
    public void testGetTrainingsByUsernameAndCriteria() {
        String username = "testUser";
        List<Training> trainingList = mock(List.class);
        when(trainingRepository.getByUsernameAndCriteria(username)).thenReturn(trainingList);

        List<Training> result = traineeService.getTrainingsByUsernameAndCriteria(username);

        assertEquals(trainingList, result);
    }
}

