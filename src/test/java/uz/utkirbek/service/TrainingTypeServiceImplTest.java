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
    void testGetAll() {
        when(trainingTypeRepository.findAll()).thenReturn(new ArrayList<>());

        List<TrainingType> result = trainingTypeService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetOne() {
        when(trainingTypeRepository.findById(1)).thenReturn(Optional.of(new TrainingType()));

        TrainingType result = trainingTypeService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void testGetOneNotFound() {
        when(trainingTypeRepository.findById(1)).thenReturn(Optional.empty());

        TrainingType result = trainingTypeService.getOne(1);

        assertNull(result);
    }

    @Test
    void testAdd() {
        when(trainingTypeRepository.create(any())).thenReturn(Optional.of(new TrainingType()));

        TrainingType result = trainingTypeService.add(new TrainingType());

        assertNotNull(result);
    }

    @Test
    void testAddRepositoryError() {
        when(trainingTypeRepository.create(any())).thenReturn(Optional.empty());

        TrainingType result = trainingTypeService.add(new TrainingType());

        assertNull(result);
    }

    @Test
    void testGetByName() {
        when(trainingTypeRepository.findByName("TrainingType")).thenReturn(Optional.of(new TrainingType()));

        TrainingType result = trainingTypeService.getByName("TrainingType");

        assertNotNull(result);
    }

    @Test
    void testGetByNameNotFound() {
        when(trainingTypeRepository.findByName("TrainingType")).thenReturn(Optional.empty());

        TrainingType result = trainingTypeService.getByName("TrainingType");

        assertNull(result);
    }
}
