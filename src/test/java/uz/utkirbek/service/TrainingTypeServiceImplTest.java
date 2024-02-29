package uz.utkirbek.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.repository.TrainingTypeRepository;
import uz.utkirbek.service.impl.TrainingTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        when(trainingTypeRepository.findAll()).thenReturn(new ArrayList<>());

        List<TrainingType> result = trainingTypeService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getAll_Exception() {
        when(trainingTypeRepository.findAll()).thenThrow(RuntimeException.class);

        List<TrainingType> result = trainingTypeService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getOne() throws Exception {
        when(trainingTypeRepository.findById(1)).thenReturn(Optional.of(new TrainingType()));

        TrainingType result = trainingTypeService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void getOne_Exception() throws Exception {
        when(trainingTypeRepository.findById(1)).thenThrow(RuntimeException.class);

        TrainingType result = trainingTypeService.getOne(1);

        assertNull(result);
    }

    @Test
    void getOneNotFound() throws Exception {
        when(trainingTypeRepository.findById(1)).thenReturn(Optional.empty());

        TrainingType result = trainingTypeService.getOne(1);

        assertNull(result);
    }

    @Test
    void add() throws Exception {
        when(trainingTypeRepository.create(any())).thenReturn(Optional.of(new TrainingType()));

        TrainingType result = trainingTypeService.add(new TrainingType());

        assertNotNull(result);
    }

    @Test
    void add_Exception() throws Exception {
        when(trainingTypeRepository.create(any())).thenThrow(RuntimeException.class);

        TrainingType result = trainingTypeService.add(new TrainingType());

        assertNull(result);
    }

    @Test
    void addRepositoryError() throws Exception {
        when(trainingTypeRepository.create(any())).thenReturn(Optional.empty());

        TrainingType result = trainingTypeService.add(new TrainingType());

        assertNull(result);
    }

    @Test
    void getByName() {
        when(trainingTypeRepository.findByName("TrainingType")).thenReturn(Optional.of(new TrainingType()));

        TrainingType result = trainingTypeService.getByName("TrainingType");

        assertNotNull(result);
    }

    @Test
    void getByName_Exception() {
        when(trainingTypeRepository.findByName("TrainingType")).thenThrow(RuntimeException.class);

        TrainingType result = trainingTypeService.getByName("TrainingType");

        assertNull(result);
    }

    @Test
    void getByNameNotFound() {
        when(trainingTypeRepository.findByName("TrainingType")).thenReturn(Optional.empty());

        TrainingType result = trainingTypeService.getByName("TrainingType");

        assertNull(result);
    }
}
