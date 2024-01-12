package uz.utkirbek.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uz.utkirbek.model.*;

import java.util.List;

@Component
@Scope("singleton")
public class ServiceFacade {
    private final BaseService<User> userService;
    private final BaseService<Trainer> trainerService;
    private final BaseService<Trainee> traineeService;
    private final BaseService<TrainingType> trainingTypeService;
    private final BaseService<Training> trainingService;


    public ServiceFacade(@Lazy BaseService<User> userService, @Lazy BaseService<Trainer> trainerService,
                         @Lazy BaseService<Trainee> traineeService, @Lazy BaseService<TrainingType> trainingTypeService,
                         @Lazy BaseService<Training> trainingService) {
        this.userService = userService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingTypeService = trainingTypeService;
        this.trainingService = trainingService;
    }

    //UserService's methods
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    public User getUser(int id){
        return userService.getOne(id);
    }

    public void addUser(User user){
        userService.add(user);
    }

    public void updateUser(User user){
        userService.update(user);
    }

    public void deleteUser(int id){
        userService.delete(id);
    }

    //TraineeService's methods
    public List<Trainee> getAllTrainees(){
        return traineeService.getAll();
    }

    public Trainee getTrainee(int id){
        return traineeService.getOne(id);
    }

    public void addTrainee(Trainee trainee){
        traineeService.add(trainee);
    }

    public void updateTrainee(Trainee trainee){
        traineeService.update(trainee);
    }

    public void deleteTrainee(int id){
        traineeService.delete(id);
    }

    //TrainerService's methods
    public List<Trainer> getAllTrainers(){
        return trainerService.getAll();
    }

    public Trainer getTrainer(int id){
        return trainerService.getOne(id);
    }

    public void addTrainer(Trainer trainer){
        trainerService.add(trainer);
    }

    public void updateTrainer(Trainer trainer){
        trainerService.update(trainer);
    }

    public void deleteTrainer(int id){
        trainerService.delete(id);
    }

    //TrainingTypeService's methods
    public List<TrainingType> getAllTrainingTypes(){
        return trainingTypeService.getAll();
    }

    public TrainingType getTrainingType(int id){
        return trainingTypeService.getOne(id);
    }

    public void addTrainingType(TrainingType trainingType){
        trainingTypeService.add(trainingType);
    }

    public void updateTrainingType(TrainingType trainingType){
        trainingTypeService.update(trainingType);
    }

    public void deleteTrainingType(int id){
        trainingTypeService.delete(id);
    }

    //TrainingService's methods
    public List<Training> getAllTrainings(){
        return trainingService.getAll();
    }

    public Training getTraining(int id){
        return trainingService.getOne(id);
    }

    public void addTraining(Training training){
        trainingService.add(training);
    }

    public void updateTraining(Training training){
        trainingService.update(training);
    }

    public void deleteTraining(int id){
        trainingService.delete(id);
    }
}
