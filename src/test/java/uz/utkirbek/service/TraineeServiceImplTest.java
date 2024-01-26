package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Training;
import uz.utkirbek.model.User;
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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll() {
        List<Trainee> traineeList = mock(List.class);
        when(traineeRepository.findAll()).thenReturn(traineeList);

        List<Trainee> result = traineeService.getAll();

        assertEquals(traineeList, result);
    }

    @Test
    public void getOneTraineeFound() {
        Trainee trainee = new Trainee();
        when(traineeRepository.findById(1)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getOne(1);

        assertEquals(trainee, result);
    }

    @Test
    public void getOneTraineeNotFound() {
        when(traineeRepository.findById(1)).thenReturn(Optional.empty());

        assertNull(traineeService.getOne(1));
    }

    @Test
    public void addTraineeCreated() {
        Trainee trainee = new Trainee();
        when(traineeRepository.create(trainee)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.add(trainee);

        assertEquals(trainee, result);
    }

    @Test
    public void addTraineeCreationFailed() {
        Trainee trainee = new Trainee();
        when(traineeRepository.create(trainee)).thenReturn(Optional.empty());

        assertNull(traineeService.add(trainee));
    }

    @Test
    public void updateTrainee() {
        Trainee trainee = new Trainee();
        when(traineeRepository.update(trainee)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.update(trainee);

        assertEquals(trainee, result);
    }

    @Test
    public void deleteTrainee() {
        Trainee trainee = new Trainee();
        when(traineeRepository.findById(1)).thenReturn(Optional.of(trainee));

        Boolean isDeleted = traineeService.delete(1);

        assertEquals(true, isDeleted);
        verify(traineeRepository, times(1)).delete(trainee);
    }

    @Test
    public void deleteTraineeNotFound() {
        when(traineeRepository.findById(1)).thenReturn(Optional.empty());

        Boolean isDeleted = traineeService.delete(1);

        assertEquals(false, isDeleted);
        verify(traineeRepository, never()).delete(any());
    }

    @Test
    public void getByUsername() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getByUsername(username);

        assertEquals(trainee, result);
    }

    @Test
    public void changePasswordSuccess() {
        int traineeId = 1;
        String password = "newPassword";
        User user = new User();
        user.setId(100);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changePassword(trainee.getUser().getId(), password)).thenReturn(Optional.of(true));

        Boolean isChanged = traineeService.changePassword(traineeId, password);

        assertEquals(true, isChanged);
    }

    @Test
    public void changePasswordTraineeNotFound() {
        int traineeId = 1;
        String password = "newPassword";
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());

        Boolean isChanged = traineeService.changePassword(traineeId, password);

        assertEquals(false, isChanged);
    }

    @Test
    public void changePasswordFailure() {
        int traineeId = 1;
        String password = "newPassword";
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(100);
        trainee.setUser(user);
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changePassword(trainee.getUser().getId(), password)).thenReturn(Optional.empty());

        Boolean isChanged = traineeService.changePassword(traineeId, password);

        assertEquals(false, isChanged);
    }

    @Test
    public void changeStatusSuccess() {
        int traineeId = 1;
        Trainee trainee = new Trainee();
        trainee.setUser(new User(100));
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changeStatus(trainee.getUser().getId())).thenReturn(Optional.of(true));

        Boolean isChanged = traineeService.changeStatus(traineeId);

        assertEquals(true, isChanged);
    }

    @Test
    public void changeStatusTraineeNotFound() {
        int traineeId = 1;
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());

        Boolean isChanged = traineeService.changeStatus(traineeId);

        assertEquals(false, isChanged);
    }

    @Test
    public void changeStatusFailure() {
        int traineeId = 1;
        Trainee trainee = new Trainee();
        trainee.setUser(new User(100));
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(userRepository.changeStatus(trainee.getUser().getId())).thenReturn(Optional.empty());

        Boolean isChanged = traineeService.changeStatus(traineeId);

        assertEquals(false, isChanged);
    }

    @Test
    public void deleteByUsername() {
        String username = "testUser";
        Trainee trainee = new Trainee();
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(trainee));

        Boolean isDeleted = traineeService.deleteByUsername(username);

        assertEquals(true, isDeleted);
        verify(traineeRepository, times(1)).delete(trainee);
    }

    @Test
    public void deleteByUsernameNotFound() {
        String username = "testUser";
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.empty());

        Boolean isDeleted = traineeService.deleteByUsername(username);

        assertEquals(false, isDeleted);
        verify(traineeRepository, never()).delete(any());
    }

    @Test
    public void getTrainingsByUsernameAndCriteria() {
        String username = "testUser";
        List<Training> trainingList = mock(List.class);
        when(trainingRepository.getByUsernameAndCriteria(username)).thenReturn(trainingList);

        List<Training> result = traineeService.getTrainingsByUsernameAndCriteria(username);

        assertEquals(trainingList, result);
    }
}
