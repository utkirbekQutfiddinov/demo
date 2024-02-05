package uz.utkirbek.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.service.TrainingTypeService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_Success() {
        List<TrainingType> trainingTypes = Arrays.asList(
                new TrainingType("Java", 1),
                new TrainingType("Spring", 2)
        );

        Mockito.when(trainingTypeService.getAll()).thenReturn(trainingTypes);

        ResponseEntity<List<TrainingType>> response = trainingTypeController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainingTypes, response.getBody());
        Mockito.verify(trainingTypeService).getAll();
    }

    @Test
    public void testGetAll_ExceptionThrown_ReturnsInternalServerError() {
        Mockito.when(trainingTypeService.getAll()).thenThrow(new RuntimeException());

        ResponseEntity<List<TrainingType>> response = trainingTypeController.getAll();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Mockito.verify(trainingTypeService).getAll();
    }
}
