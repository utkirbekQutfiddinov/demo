package uz.utkirbek.service;

import org.springframework.stereotype.Component;
import uz.utkirbek.model.*;

import java.util.List;

@Component
public class ServiceFacade {
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;
    private final TrainingService trainingService;
    private final TrainerService trainerService;
    private final TraineeService traineeService;


    public ServiceFacade(UserService userService, TrainingTypeService trainingTypeService, TrainingService trainingService, TrainerService trainerService, TraineeService traineeService) {
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
        this.trainingService = trainingService;
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    //UserService's methods
    public User createUser(User item){
        return userService.add(item);
    }
    public User getUser(Integer id){
        return userService.getOne(id);
    }
    public List<User> getAllUsers(){
        return userService.getAll();
    }
    public User updateUser(User item){
        return userService.update(item);
    }
    public Boolean deleteUser(Integer id){
        return userService.delete(id);
    }

    //TrainingTypeService's methods
    public TrainingType createTrainingType(TrainingType item){
        return trainingTypeService.add(item);
    }
    public TrainingType getTrainingType(Integer id){
        return trainingTypeService.getOne(id);
    }
    public List<TrainingType> getAllTrainingTypes(){
        return trainingTypeService.getAll();
    }

    //TrainingService's methods
    public Training createTraining(Training item){
        return trainingService.add(item);
    }
    public Training getTraining(Integer id){
        return trainingService.getOne(id);
    }
    public List<Training> getAllTrainings(){
        return trainingService.getAll();
    }

    //TrainerService's methods
    public Trainer createTrainer(Trainer item){
        return trainerService.add(item);
    }
    public Trainer getTrainer(Integer id){
        return trainerService.getOne(id);
    }
    public List<Trainer> getAllTrainers(){
        return trainerService.getAll();
    }
    public Trainer updateTrainer(Trainer item){
        return trainerService.update(item);
    }

    //TraineeService's methods
    public Trainee createTrainee(Trainee item){
        return traineeService.add(item);
    }
    public Trainee getTrainee(Integer id){
        return traineeService.getOne(id);
    }
    public List<Trainee> getAllTrainees(){
        return traineeService.getAll();
    }
    public Trainee updateTrainee(Trainee item){
        return traineeService.update(item);
    }
    public Boolean deleteTrainee(Integer id){
        return traineeService.delete(id);
    }


}
