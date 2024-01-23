package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.repository.TrainingTypeRepository;
import uz.utkirbek.service.impl.TrainingTypeServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TrainingTypeServiceImplTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll() {
        List<TrainingType> trainingTypeList = mock(List.class);
        when(trainingTypeRepository.readAll()).thenReturn(trainingTypeList);

        List<TrainingType> result = trainingTypeService.getAll();

        assertEquals(trainingTypeList, result);
    }

    @Test
    public void getOneTrainingTypeFound() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.readOne(1)).thenReturn(Optional.of(trainingType));

        TrainingType result = trainingTypeService.getOne(1);

        assertEquals(trainingType, result);
    }

    @Test
    public void getOneTrainingTypeNotFound() {
        when(trainingTypeRepository.readOne(1)).thenReturn(Optional.empty());

        assertNull(trainingTypeService.getOne(1));
    }

    @Test
    public void addTrainingTypeCreated() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.create(trainingType)).thenReturn(Optional.of(trainingType));

        TrainingType result = trainingTypeService.add(trainingType);

        assertEquals(trainingType, result);
    }

    @Test
    public void addTrainingTypeCreationFailed() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.create(trainingType)).thenReturn(Optional.empty());

        assertNull(trainingTypeService.add(trainingType));
    }
}

