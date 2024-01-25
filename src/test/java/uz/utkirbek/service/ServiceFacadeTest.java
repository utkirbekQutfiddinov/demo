package uz.utkirbek.service;

import org.junit.jupiter.api.Test;
import uz.utkirbek.model.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceFacadeTest {
    UserService userService = mock(UserService.class);
    TraineeService traineeService = mock(TraineeService.class);
    TrainerService trainerService = mock(TrainerService.class);
    TrainingTypeService trainingTypeService = mock(TrainingTypeService.class);
    TrainingService trainingService = mock(TrainingService.class);
    ServiceFacade facade = new ServiceFacade(userService, trainingTypeService, trainingService, trainerService, traineeService);

    @Test
    void createUser() {

        User userToCreate = new User();

        when(userService.add(userToCreate)).thenReturn(userToCreate);

        User createdUser = facade.createUser(userToCreate);

        assertNotNull(createdUser);
        assertEquals(userToCreate, createdUser);
    }

    @Test
    void getUser() {

        Integer userId = 1;
        User expectedUser = new User();

        when(userService.getOne(userId)).thenReturn(expectedUser);

        User actualUser = facade.getUser(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void getAllUsers() {

        List<User> expectedUsers = Arrays.asList(
                new User(),
                new User()
        );

        when(userService.getAll()).thenReturn(expectedUsers);

        List<User> actualUsers = facade.getAllUsers();

        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
    }

    @Test
    void updateUser() {

        User userToUpdate = new User();

        when(userService.update(userToUpdate)).thenReturn(userToUpdate);

        User updatedUser = facade.updateUser(userToUpdate);

        assertNotNull(updatedUser);
        assertEquals(userToUpdate, updatedUser);
    }

    @Test
    void deleteUser() {

        Integer userId = 1;

        when(userService.delete(userId)).thenReturn(true);

        boolean isDeleted = facade.deleteUser(userId);

        assertTrue(isDeleted);
    }

    @Test
    void createTrainingType() {

        TrainingType trainingTypeToCreate = new TrainingType();

        when(trainingTypeService.add(trainingTypeToCreate)).thenReturn(trainingTypeToCreate);

        TrainingType createdTrainingType = facade.createTrainingType(trainingTypeToCreate);

        assertNotNull(createdTrainingType);
        assertEquals(trainingTypeToCreate, createdTrainingType);
    }

    @Test
    void getTrainingType() {

        Integer userId = 1;
        TrainingType expectedTrainingType = new TrainingType();

        when(trainingTypeService.getOne(userId)).thenReturn(expectedTrainingType);

        TrainingType actualTrainingType = facade.getTrainingType(userId);

        assertNotNull(actualTrainingType);
        assertEquals(expectedTrainingType, actualTrainingType);
    }

    @Test
    void getAllTrainingTypes() {

        List<TrainingType> expectedTrainingTypes = Arrays.asList(
                new TrainingType(),
                new TrainingType()
        );

        when(trainingTypeService.getAll()).thenReturn(expectedTrainingTypes);

        List<TrainingType> actualTrainingTypes = facade.getAllTrainingTypes();

        assertEquals(expectedTrainingTypes.size(), actualTrainingTypes.size());
        assertTrue(actualTrainingTypes.containsAll(expectedTrainingTypes));
    }

    @Test
    void createTraining() {

        Training trainingToCreate = new Training();

        when(trainingService.add(trainingToCreate)).thenReturn(trainingToCreate);

        Training createdTraining = facade.createTraining(trainingToCreate);

        assertNotNull(createdTraining);
        assertEquals(trainingToCreate, createdTraining);
    }

    @Test
    void getTraining() {

        Integer userId = 1;
        Training expectedTraining = new Training();

        when(trainingService.getOne(userId)).thenReturn(expectedTraining);

        Training actualTraining = facade.getTraining(userId);

        assertNotNull(actualTraining);
        assertEquals(expectedTraining, actualTraining);
    }

    @Test
    void getAllTrainings() {

        List<Training> expectedTrainings = Arrays.asList(
                new Training(),
                new Training()
        );

        when(trainingService.getAll()).thenReturn(expectedTrainings);

        List<Training> actualTrainings = facade.getAllTrainings();

        assertEquals(expectedTrainings.size(), actualTrainings.size());
        assertTrue(actualTrainings.containsAll(expectedTrainings));
    }

    @Test
    void createTrainee() {

        Trainee traineeToCreate = new Trainee();

        when(traineeService.add(traineeToCreate)).thenReturn(traineeToCreate);

        Trainee createdTrainee = facade.createTrainee(traineeToCreate);

        assertNotNull(createdTrainee);
        assertEquals(traineeToCreate, createdTrainee);
    }

    @Test
    void getTrainee() {

        Integer userId = 1;
        Trainee expectedTrainee = new Trainee();

        when(traineeService.getOne(userId)).thenReturn(expectedTrainee);

        Trainee actualTrainee = facade.getTrainee(userId);

        assertNotNull(actualTrainee);
        assertEquals(expectedTrainee, actualTrainee);
    }

    @Test
    void getAllTrainees() {

        List<Trainee> expectedTrainees = Arrays.asList(
                new Trainee(),
                new Trainee()
        );

        when(traineeService.getAll()).thenReturn(expectedTrainees);

        List<Trainee> actualTrainees = facade.getAllTrainees();

        assertEquals(expectedTrainees.size(), actualTrainees.size());
        assertTrue(actualTrainees.containsAll(expectedTrainees));
    }

    @Test
    void updateTrainee() {

        Trainee traineeToUpdate = new Trainee();

        when(traineeService.update(traineeToUpdate)).thenReturn(traineeToUpdate);

        Trainee updatedTrainee = facade.updateTrainee(traineeToUpdate);

        assertNotNull(updatedTrainee);
        assertEquals(traineeToUpdate, updatedTrainee);
    }

    @Test
    void deleteTrainee() {

        Integer userId = 1;

        when(traineeService.delete(userId)).thenReturn(true);

        boolean isDeleted = facade.deleteTrainee(userId);

        assertTrue(isDeleted);
    }

    @Test
    void createTrainer() {

        Trainer trainerToCreate = new Trainer();

        when(trainerService.add(trainerToCreate)).thenReturn(trainerToCreate);

        Trainer createdTrainer = facade.createTrainer(trainerToCreate);

        assertNotNull(createdTrainer);
        assertEquals(trainerToCreate, createdTrainer);
    }

    @Test
    void getTrainer() {

        Integer userId = 1;
        Trainer expectedTrainer = new Trainer();

        when(trainerService.getOne(userId)).thenReturn(expectedTrainer);

        Trainer actualTrainer = facade.getTrainer(userId);

        assertNotNull(actualTrainer);
        assertEquals(expectedTrainer, actualTrainer);
    }

    @Test
    void getAllTrainers() {

        List<Trainer> expectedTrainers = Arrays.asList(
                new Trainer(),
                new Trainer()
        );

        when(trainerService.getAll()).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = facade.getAllTrainers();

        assertEquals(expectedTrainers.size(), actualTrainers.size());
        assertTrue(actualTrainers.containsAll(expectedTrainers));
    }

    @Test
    void updateTrainer() {

        Trainer trainerToUpdate = new Trainer();

        when(trainerService.update(trainerToUpdate)).thenReturn(trainerToUpdate);

        Trainer updatedTrainer = facade.updateTrainer(trainerToUpdate);

        assertNotNull(updatedTrainer);
        assertEquals(trainerToUpdate, updatedTrainer);
    }

}
