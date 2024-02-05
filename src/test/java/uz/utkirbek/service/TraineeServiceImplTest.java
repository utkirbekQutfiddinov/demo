package uz.utkirbek.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.TraineeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        when(traineeRepository.findAll()).thenReturn(new ArrayList<>());

        List<Trainee> result = traineeService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetOne() {
        when(traineeRepository.findById(1)).thenReturn(Optional.of(new Trainee()));

        Trainee result = traineeService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void testGetOneNotFound() {
        when(traineeRepository.findById(1)).thenReturn(Optional.empty());

        Trainee result = traineeService.getOne(1);

        assertNull(result);
    }

    @Test
    void testAdd() {
        when(traineeRepository.create(any())).thenReturn(Optional.of(new Trainee()));

        Trainee result = traineeService.add(new TraineeDto());

        assertNotNull(result);
    }

    @Test
    void testAddRepositoryError() {
        when(traineeRepository.create(any())).thenReturn(Optional.empty());

        Trainee result = traineeService.add(new TraineeDto());

        assertNull(result);
    }

    @Test
    void testUpdate() {
        when(traineeRepository.update(any())).thenReturn(Optional.of(new Trainee()));

        Trainee result = traineeService.update(new Trainee());

        assertNotNull(result);
    }

    @Test
    void testUpdateRepositoryError() {
        when(traineeRepository.update(any())).thenReturn(Optional.empty());

        Trainee result = traineeService.update(new Trainee());

        assertNull(result);
    }

    @Test
    void testDelete() {
        when(traineeRepository.findById(1)).thenReturn(Optional.of(new Trainee()));

        boolean result = traineeService.delete(1);

        assertTrue(result);
    }

    @Test
    void testDeleteNotFound() {
        when(traineeRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = traineeService.delete(1);

        assertFalse(result);
    }

    @Test
    void testGetByUsername() {
        when(traineeRepository.findByUsername("username")).thenReturn(Optional.of(new Trainee()));

        Trainee result = traineeService.getByUsername("username");

        assertNotNull(result);
    }

    @Test
    void testGetByUsernameNotFound() {
        when(traineeRepository.findByUsername("username")).thenReturn(Optional.empty());

        Trainee result = traineeService.getByUsername("username");

        assertNull(result);
    }

    @Test
    void testChangeStatus() {
        when(userRepository.changeStatus("username", true)).thenReturn(Optional.of(true));

        boolean result = traineeService.changeStatus("username", true);

        assertTrue(result);
    }

    @Test
    void testChangeStatusRepositoryError() {
        when(userRepository.changeStatus("username", true)).thenReturn(Optional.empty());

        boolean result = traineeService.changeStatus("username", true);

        assertFalse(result);
    }

    @Test
    void testGetNotAssignedAcitiveTrainers() {
        when(traineeRepository.getNotAssignedActiveTrainers("username")).thenReturn(new ArrayList<>());

        List<TraineeTrainerResponse> result = traineeService.getNotAssignedAcitiveTrainers("username");

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
