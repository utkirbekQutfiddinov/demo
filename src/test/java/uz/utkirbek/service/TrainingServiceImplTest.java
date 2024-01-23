package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.service.impl.TrainingServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Training> trainingList = mock(List.class);
        when(trainingRepository.readAll()).thenReturn(trainingList);

        List<Training> result = trainingService.getAll();

        assertEquals(trainingList, result);
    }

    @Test
    public void testGetOneTrainingFound() {
        Training training = new Training();
        when(trainingRepository.readOne(1)).thenReturn(Optional.of(training));

        Training result = trainingService.getOne(1);

        assertEquals(training, result);
    }

    @Test
    public void testGetOneTrainingNotFound() {
        when(trainingRepository.readOne(1)).thenReturn(Optional.empty());

        assertNull(trainingService.getOne(1));
    }

    @Test
    public void testAddTrainingCreated() {
        Training training = new Training();
        when(trainingRepository.create(training)).thenReturn(Optional.of(training));

        Training result = trainingService.add(training);

        assertEquals(training, result);
    }

    @Test
    public void testAddTrainingCreationFailed() {
        Training training = new Training();
        when(trainingRepository.create(training)).thenReturn(Optional.empty());

        assertNull(trainingService.add(training));
    }

    @Test
    public void testGetByUsernameAndCriteria() {
        String username = "testUser";
        List<Training> trainingList = mock(List.class);
        when(trainingRepository.getByUsernameAndCriteria(username)).thenReturn(trainingList);

        List<Training> result = trainingService.getByUsernameAndCriteria(username);

        assertEquals(trainingList, result);
    }

    @Test
    public void testUpdateTrainerSuccess() {
        int trainingId = 1;
        Trainer trainer = new Trainer();
        when(trainingRepository.updateTrainer(trainingId, trainer)).thenReturn(Optional.of(true));

        Boolean result = trainingService.updateTrainer(trainingId, trainer);

        assertEquals(true, result);
    }

    @Test
    public void testUpdateTrainerFailure() {
        int trainingId = 1;
        Trainer trainer = new Trainer();
        when(trainingRepository.updateTrainer(trainingId, trainer)).thenReturn(Optional.empty());

        Boolean result = trainingService.updateTrainer(trainingId, trainer);

        assertEquals(false, result);
    }
}

