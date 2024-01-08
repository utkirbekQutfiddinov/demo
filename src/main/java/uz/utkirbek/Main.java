package uz.utkirbek;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.User;
import uz.utkirbek.service.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
        UserService userService = context.getBean(UserService.class);
//        TrainerService trainerService = context.getBean(TrainerService.class);
//        TraineeService traineeService = context.getBean(TraineeService.class);
//        TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);
//        TrainingService trainingService = context.getBean(TrainingService.class);

        System.out.println("Before:");
        List<User> users = userService.getAll();
        for (User user : users) {
            System.out.println(user.getFirstname() + " - " + user.getLastname() + " - " + user.getActive());
        }

        User newUser = new User();
        newUser.setFirstname("Utkirbek");
        newUser.setLastname("Qutfiddinov");
        userService.add(newUser);

        System.out.println("After:");
        users = userService.getAll();
        for (User user : users) {
            System.out.println(user.getFirstname() + " - " + user.getLastname() + " - " + user.getActive());
        }

    }
}
