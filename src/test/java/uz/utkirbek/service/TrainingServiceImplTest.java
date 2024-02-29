package uz.utkirbek.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.service.impl.TrainingServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        when(trainingRepository.findAll()).thenReturn(new ArrayList<>());

        List<Training> result = trainingService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getAll_Exception() {
        when(trainingRepository.findAll()).thenThrow(RuntimeException.class);

        List<Training> result = trainingService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getOne() throws Exception {
        when(trainingRepository.findById(1)).thenReturn(Optional.of(new Training()));

        Training result = trainingService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void getOne_Exception() throws Exception {
        when(trainingRepository.findById(1)).thenThrow(RuntimeException.class);

        Training result = trainingService.getOne(1);

        assertNull(result);
    }

    @Test
    void getOneNotFound() throws Exception {
        when(trainingRepository.findById(1)).thenReturn(Optional.empty());

        Training result = trainingService.getOne(1);

        assertNull(result);
    }

    @Test
    void add() throws Exception {
        when(trainingRepository.create(any())).thenReturn(Optional.of(new Training()));

        Training result = trainingService.add(new TrainingDto());

        assertNotNull(result);
    }

    @Test
    void add_Exception() throws Exception {
        when(trainingRepository.create(any())).thenThrow(RuntimeException.class);

        Training result = trainingService.add(new TrainingDto());

        assertNull(result);
    }

    @Test
    void addRepositoryError() throws Exception {
        when(trainingRepository.create(any())).thenReturn(Optional.empty());

        Training result = trainingService.add(new TrainingDto());

        assertNull(result);
    }

    @Test
    void getByCriteria() {
        when(trainingRepository.getByCriteria(any())).thenReturn(new ArrayList<>());

        List<TrainingResponse> result = trainingService.getByCriteria(new TrainingFiltersDto());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void updateTrainer() {
        when(trainingRepository.updateTrainer(1, "trainerUsername")).thenReturn(Optional.of(true));

        Boolean result = trainingService.updateTrainer(1, "trainerUsername");

        assertTrue(result);
    }

    @Test
    void updateTrainer_Exception() {
        when(trainingRepository.updateTrainer(1, "trainerUsername")).thenThrow(RuntimeException.class);

        Boolean result = trainingService.updateTrainer(1, "trainerUsername");

        assertFalse(result);
    }

    @Test
    void updateTrainerRepositoryError() {
        when(trainingRepository.updateTrainer(1, "trainerUsername")).thenReturn(Optional.empty());

        Boolean result = trainingService.updateTrainer(1, "trainerUsername");

        assertFalse(result);
    }
}
