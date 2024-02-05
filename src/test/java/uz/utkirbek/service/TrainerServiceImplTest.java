package uz.utkirbek.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.repository.TrainerRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.TrainerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        when(trainerRepository.findAll()).thenReturn(new ArrayList<>());

        List<Trainer> result = trainerService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetOne() {
        when(trainerRepository.findById(1)).thenReturn(Optional.of(new Trainer()));

        Trainer result = trainerService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void testGetOneNotFound() {
        when(trainerRepository.findById(1)).thenReturn(Optional.empty());

        Trainer result = trainerService.getOne(1);

        assertNull(result);
    }

    @Test
    void testAdd() {
        when(trainerRepository.create(any())).thenReturn(Optional.of(new Trainer()));

        Trainer result = trainerService.add(new TrainerDto());

        assertNotNull(result);
    }

    @Test
    void testAddRepositoryError() {
        when(trainerRepository.create(any())).thenReturn(Optional.empty());

        Trainer result = trainerService.add(new TrainerDto());

        assertNull(result);
    }

    @Test
    void testUpdate() {
        when(trainerRepository.update(any())).thenReturn(Optional.of(new Trainer()));

        Trainer result = trainerService.update(new Trainer());

        assertNotNull(result);
    }

    @Test
    void testUpdateRepositoryError() {
        when(trainerRepository.update(any())).thenReturn(Optional.empty());

        Trainer result = trainerService.update(new Trainer());

        assertNull(result);
    }

    @Test
    void testGetByUsername() {
        when(trainerRepository.findByUsername("username")).thenReturn(Optional.of(new Trainer()));

        Trainer result = trainerService.getByUsername("username");

        assertNotNull(result);
    }

    @Test
    void testGetByUsernameNotFound() {
        when(trainerRepository.findByUsername("username")).thenReturn(Optional.empty());

        Trainer result = trainerService.getByUsername("username");

        assertNull(result);
    }

    @Test
    void testChangeStatus() {
        when(userRepository.changeStatus("username", true)).thenReturn(Optional.of(true));

        boolean result = trainerService.changeStatus("username", true);

        assertTrue(result);
    }

    @Test
    void testChangeStatusRepositoryError() {
        when(userRepository.changeStatus("username", true)).thenReturn(Optional.empty());

        boolean result = trainerService.changeStatus("username", true);

        assertFalse(result);
    }
}