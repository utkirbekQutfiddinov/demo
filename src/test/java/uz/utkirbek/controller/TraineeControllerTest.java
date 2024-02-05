package uz.utkirbek.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.dto.TraineeUpdateDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.model.response.RegisterResponse;
import uz.utkirbek.model.response.TraineeResponse;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.model.response.TraineeTrainingResponse;
import uz.utkirbek.service.TraineeService;
import uz.utkirbek.service.TrainingService;
import uz.utkirbek.service.UserService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";
        User user = new User(firstname, lastname);
        user.setUsername(firstname + lastname);
        user.setPassword("mockPassword");

        when(traineeService.add(any())).thenReturn(new Trainee(user, null, null));

        TraineeDto traineeDto = new TraineeDto(firstname, lastname, null, null);
        ResponseEntity<RegisterResponse> response = traineeController.register(traineeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getUsername());
        assertNotNull(response.getBody().getPassword());
    }

    @Test
    void testRegisterBadRequest() {
        ResponseEntity<RegisterResponse> response = traineeController.register(new TraineeDto());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegisterError() {
        when(traineeService.add(any())).thenReturn(null);

        TraineeDto traineeDto = new TraineeDto();
        ResponseEntity<RegisterResponse> response = traineeController.register(traineeDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetByUsernameSuccess() {

        String firstname = "utkirbek";
        String lastname = "qutfiddinov";
        User user = new User(firstname, lastname);
        user.setUsername(firstname + lastname);
        user.setPassword("mockPassword");


        Trainee trainee = new Trainee(user, null, null);
        trainee.setTrainings(Collections.emptyList());

        when(traineeService.getByUsername(anyString())).thenReturn(trainee);

        ResponseEntity<TraineeResponse> response = traineeController.getByUsername("testUsername");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetByUsernameBadRequest() {
        ResponseEntity<TraineeResponse> response = traineeController.getByUsername(null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetByUsernameNotFound() {
        when(traineeService.getByUsername(anyString())).thenReturn(null);

        ResponseEntity<TraineeResponse> response = traineeController.getByUsername("nonExistentUsername");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateProfileSuccess() {

        String firstname = "utkirbek";
        String lastname = "qutfiddinov";
        User user = new User(firstname, lastname);
        user.setUsername(firstname + lastname);
        user.setPassword("mockPassword");


        Trainee trainee = new Trainee(user, null, null);
        trainee.setTrainings(Collections.emptyList());

        when(traineeService.getByUsername(anyString())).thenReturn(trainee);

        when(traineeService.update(any())).thenReturn(trainee);
        when(userService.update(any())).thenReturn(user);

        TraineeUpdateDto updateDto = new TraineeUpdateDto("utkirbekQutfiddinov", "Utkirbek", "Qutfiddinov", null, null, false);
        ResponseEntity<TraineeResponse> response = traineeController.updateProfile(updateDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateProfileBadRequest() {
        ResponseEntity<TraineeResponse> response = traineeController.updateProfile(null);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateProfileTraineeNotFound() {

        when(traineeService.getByUsername(anyString())).thenReturn(null);

        TraineeUpdateDto updateDto = new TraineeUpdateDto("utkir", "Utkirbek", "Qutfiddinov", null, null, false);

        ResponseEntity<TraineeResponse> response = traineeController.updateProfile(updateDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateProfileTraineeUpdateError() {
        when(traineeService.getByUsername(anyString())).thenReturn(new Trainee());
        when(traineeService.update(any())).thenReturn(null);

        TraineeUpdateDto updateDto = new TraineeUpdateDto();
        ResponseEntity<TraineeResponse> response = traineeController.updateProfile(updateDto);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDelete_ValidUsername_ReturnsSuccess() {
        String username = "testUsername";
        Trainee trainee = new Trainee();
        trainee.setId(1);

        when(traineeService.getByUsername(username)).thenReturn(trainee);
        when(traineeService.delete(trainee.getId())).thenReturn(true);

        ResponseEntity<String> response = traineeController.delete(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        verify(traineeService).getByUsername(username);
        verify(traineeService).delete(trainee.getId());
    }

    @Test
    public void testDelete_NonexistentUsername_ReturnsNotFound() {
        String username = "nonexistentUser";
        when(traineeService.getByUsername(username)).thenReturn(null);

        ResponseEntity<String> response = traineeController.delete(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error", response.getBody());
        verify(traineeService).getByUsername(username);
    }

    @Test
    public void testDelete_EmptyUsername_ReturnsBadRequest() {
        ResponseEntity<String> response = traineeController.delete(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verifyNoInteractions(traineeService);
    }

    @Test
    public void testGetTrainings_ValidParameters_ReturnsOk() {
        String username = "testUsername";
        LocalDate periodFrom = LocalDate.of(2023, 1, 1);
        LocalDate periodTo = LocalDate.of(2023, 12, 31);
        String trainerUsername = "testTrainer";
        String trainingType = "testType";

        Mockito.when(trainingService.getByCriteria(Mockito.any())).thenReturn(Collections.emptyList());

        ResponseEntity<List<TraineeTrainingResponse>> response = traineeController.getTrainings(
                username, periodFrom, periodTo, trainerUsername, trainingType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
        Mockito.verify(trainingService).getByCriteria(Mockito.any());
    }

    @Test
    public void testGetTrainings_EmptyUsername_ReturnsBadRequest() {
        ResponseEntity<List<TraineeTrainingResponse>> response = traineeController.getTrainings(
                null, LocalDate.now(), LocalDate.now(), null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(trainingService);
    }

    @Test
    public void testGetTrainings_InternalServerError_ReturnsInternalServerError() {
        String username = "testUsername";
        LocalDate periodFrom = LocalDate.of(2023, 1, 1);
        LocalDate periodTo = LocalDate.of(2023, 12, 31);
        String trainerUsername = "testTrainer";
        String trainingType = "testType";

        Mockito.when(trainingService.getByCriteria(Mockito.any())).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<List<TraineeTrainingResponse>> response = traineeController.getTrainings(
                username, periodFrom, periodTo, trainerUsername, trainingType);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(trainingService).getByCriteria(Mockito.any());
    }

    @Test
    public void testGetActiveNotAssignedTrainers_ValidParameters_ReturnsOk() {
        String username = "testUsername";
        Mockito.when(traineeService.getNotAssignedAcitiveTrainers(username)).thenReturn(Collections.emptyList());

        ResponseEntity<List<TraineeTrainerResponse>> response = traineeController.getActiveNotAssignedTrainers(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
        Mockito.verify(traineeService).getNotAssignedAcitiveTrainers(username);
    }

    @Test
    public void testGetActiveNotAssignedTrainers_EmptyUsername_ReturnsBadRequest() {
        ResponseEntity<List<TraineeTrainerResponse>> response = traineeController.getActiveNotAssignedTrainers(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(traineeService);
    }

    @Test
    public void testGetActiveNotAssignedTrainers_InternalServerError_ReturnsInternalServerError() {
        String username = "testUsername";
        Mockito.when(traineeService.getNotAssignedAcitiveTrainers(username)).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<List<TraineeTrainerResponse>> response = traineeController.getActiveNotAssignedTrainers(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(traineeService).getNotAssignedAcitiveTrainers(username);
    }

    @Test
    public void testChangeStatus_ValidParameters_ReturnsOk() {
        String username = "testUsername";
        Boolean isActive = true;
        Mockito.when(traineeService.changeStatus(username, isActive)).thenReturn(true);

        ResponseEntity<String> response = traineeController.changeStatus(username, isActive);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        Mockito.verify(traineeService).changeStatus(username, isActive);
    }

    @Test
    public void testChangeStatus_EmptyUsername_ReturnsBadRequest() {
        ResponseEntity<String> response = traineeController.changeStatus(null, true);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(traineeService);
    }

    @Test
    public void testChangeStatus_NullIsActive_ReturnsBadRequest() {
        ResponseEntity<String> response = traineeController.changeStatus("testUsername", null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(traineeService);
    }

    @Test
    public void testChangeStatus_InternalServerError_ReturnsInternalServerError() {
        String username = "testUsername";
        Boolean isActive = true;
        Mockito.when(traineeService.changeStatus(username, isActive)).thenReturn(false);

        ResponseEntity<String> response = traineeController.changeStatus(username, isActive);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(traineeService).changeStatus(username, isActive);
    }
}
