package uz.utkirbek.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.dto.TrainerUpdateDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.model.response.RegisterResponse;
import uz.utkirbek.model.response.TrainerResponse;
import uz.utkirbek.model.response.TrainerTrainingResponse;
import uz.utkirbek.service.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private UserService userService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Success() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        Trainer addedTrainer = new Trainer();
        User user = new User(firstname, lastname);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName(firstname);
        trainerDto.setLastName(lastname);
        trainerDto.setSpecialization(new TrainingType("Java", 1));

        addedTrainer.setUser(user);
        when(trainerService.add(trainerDto)).thenReturn(addedTrainer);

        ResponseEntity<RegisterResponse> response = trainerController.register(trainerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testRegister_BadRequest() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        Trainer addedTrainer = new Trainer();
        User user = new User(firstname, lastname);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setSpecialization(new TrainingType("Java", 1));

        addedTrainer.setUser(user);
        when(trainerService.add(trainerDto)).thenReturn(addedTrainer);

        ResponseEntity<RegisterResponse> response = trainerController.register(trainerDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegister_ErrorDuringCreating() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        Trainer addedTrainer = new Trainer();
        User user = new User(firstname, lastname);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName(firstname);
        trainerDto.setLastName(lastname);
        trainerDto.setSpecialization(new TrainingType("Java", 1));

        addedTrainer.setUser(user);
        when(trainerService.add(trainerDto)).thenReturn(null);

        ResponseEntity<RegisterResponse> response = trainerController.register(trainerDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testRegister_InternalServerError() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        Trainer addedTrainer = new Trainer();
        User user = new User(firstname, lastname);

        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setFirstName(firstname);
        trainerDto.setLastName(lastname);
        trainerDto.setSpecialization(new TrainingType("Java", 1));

        addedTrainer.setUser(user);
        when(trainerService.add(trainerDto)).thenThrow(RuntimeException.class);

        ResponseEntity<RegisterResponse> response = trainerController.register(trainerDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetByUsername_Success() {
        String username = "utkir";

        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        Trainer trainer = new Trainer();
        User user = new User(firstname, lastname);
        trainer.setUser(user);

        when(trainerService.getByUsername(username)).thenReturn(trainer);

        ResponseEntity<TrainerResponse> response = trainerController.getByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetByUsername_NotFound() {
        String username = "utkir";

        when(trainerService.getByUsername(username)).thenReturn(null);

        ResponseEntity<TrainerResponse> response = trainerController.getByUsername(username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetByUsername_InternaServerError() {
        String username = "utkir";

        Trainer trainer = new Trainer();

        when(trainerService.getByUsername(username)).thenReturn(trainer);

        ResponseEntity<TrainerResponse> response = trainerController.getByUsername(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateProfile_Success() {

        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        TrainerUpdateDto dto = new TrainerUpdateDto();
        dto.setUsername(firstname + lastname);
        dto.setFirstName(firstname);
        dto.setLastName(lastname);
        dto.setSpecialization(new TrainingType("Java", 1));
        dto.setActive(true);


        Trainer trainer = new Trainer();
        User user = new User(firstname, lastname);
        trainer.setUser(user);

        when(trainerService.getByUsername(dto.getUsername())).thenReturn(trainer);

        ResponseEntity<TrainerResponse> response = trainerController.updateProfile(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    void testUpdateProfile_BadRequest() {

        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        TrainerUpdateDto dto = new TrainerUpdateDto();
        dto.setUsername(firstname + lastname);
        dto.setFirstName(firstname);
        dto.setLastName(lastname);
        dto.setSpecialization(new TrainingType("Java", 1));
        dto.setActive(true);


        Trainer trainer = new Trainer();
        User user = new User(firstname, lastname);
        trainer.setUser(user);

        when(trainerService.getByUsername(dto.getUsername())).thenReturn(trainer);

        ResponseEntity<TrainerResponse> response = trainerController.updateProfile(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateProfile_TrainerNotFound() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        TrainerUpdateDto dto = new TrainerUpdateDto();
        dto.setUsername(firstname + lastname);
        dto.setFirstName(firstname);
        dto.setLastName(lastname);
        dto.setSpecialization(new TrainingType("Java", 1));
        dto.setActive(true);


        Trainer trainer = new Trainer();
        User user = new User(firstname, lastname);
        trainer.setUser(user);

        when(trainerService.getByUsername(dto.getUsername())).thenReturn(null);

        ResponseEntity<TrainerResponse> response = trainerController.updateProfile(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateProfile_InternalServerError() {
        String firstname = "utkirbek";
        String lastname = "qutfiddinov";

        TrainerUpdateDto dto = new TrainerUpdateDto();
        dto.setUsername(firstname + lastname);
        dto.setFirstName(firstname);
        dto.setLastName(lastname);
        dto.setSpecialization(new TrainingType("Java", 1));
        dto.setActive(true);


        Trainer trainer = new Trainer();

        when(trainerService.getByUsername(dto.getUsername())).thenReturn(trainer);

        ResponseEntity<TrainerResponse> response = trainerController.updateProfile(dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetTrainings_Succes() {
        String username = "utkirbek";
        LocalDate periodFrom = LocalDate.now().minusDays(7);
        LocalDate periodTo = LocalDate.now();

        ResponseEntity<List<TrainerTrainingResponse>> response =
                trainerController.getTrainings(username, periodFrom, periodTo, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetTrainings_InternalServerError() {
        String username = "utkirbek";
        LocalDate periodFrom = LocalDate.now().minusDays(7);
        LocalDate periodTo = LocalDate.now();

        when(trainingService.getByCriteria(any())).thenReturn(null);

        ResponseEntity<List<TrainerTrainingResponse>> response =
                trainerController.getTrainings(username, periodFrom, periodTo, null, null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testChangeStatus_Success() {
        String username = "utkirbek";
        Boolean isActive = true;

        when(trainerService.changeStatus(username, isActive)).thenReturn(true);

        ResponseEntity<String> response = trainerController.changeStatus(username, isActive);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testChangeStatus_BadRequest() {
        String username = null;
        Boolean isActive = true;

        when(trainerService.changeStatus(username, isActive)).thenReturn(true);

        ResponseEntity<String> response = trainerController.changeStatus(username, isActive);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testChangeStatus_NotChanged() {
        String username = "utkirbek";
        Boolean isActive = true;

        when(trainerService.changeStatus(username, isActive)).thenReturn(false);

        ResponseEntity<String> response = trainerController.changeStatus(username, isActive);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}

