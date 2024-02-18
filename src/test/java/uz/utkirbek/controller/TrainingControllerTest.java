package uz.utkirbek.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingUpdateDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.service.TrainingService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController trainingController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add_Success() {
        TrainingDto validDto = new TrainingDto();
        validDto.setDate(new Date());
        validDto.setDuration(60);
        validDto.setName("Training Name");
        validDto.setTraineeUsername("traineeUsername");
        validDto.setTrainerUsername("trainerUsername");

        when(trainingService.add(validDto)).thenReturn(new Training());

        ResponseEntity<String> response = trainingController.add(validDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(trainingService).add(validDto);
    }

    @Test
    public void add_BadRequest() {
        TrainingDto invalidDto = new TrainingDto();

        ResponseEntity<String> response = trainingController.add(invalidDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(trainingService, Mockito.never()).add(invalidDto);
    }

    @Test
    public void add_Failure() {
        TrainingDto validDto = new TrainingDto();
        validDto.setDate(new Date());
        validDto.setDuration(60);
        validDto.setName("Training Name");
        validDto.setTraineeUsername("traineeUsername");
        validDto.setTrainerUsername("trainerUsername");

        when(trainingService.add(validDto)).thenReturn(null);

        ResponseEntity<String> response = trainingController.add(validDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(trainingService).add(validDto);
    }
}
