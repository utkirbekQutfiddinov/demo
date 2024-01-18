package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Training;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TrainingServiceImpl {

//    private ServiceFacade serviceFacade;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
//        serviceFacade = context.getBean(ServiceFacade.class);
    }

    @Test
    public void testGetAll() throws ParseException {

        for (int i = 0; i < 10; i++) {
            Training newTraining = createTraining(i, i*2,i*3,
                    "Utkirbek",i,"2024-01-20",15);
//            serviceFacade.addTraining(newTraining);
        }

//        List<Training> allTrainings = serviceFacade.getAllTrainings();

//        assertEquals(10, allTrainings.size());
    }

    @Test
    public void testGetOne() throws ParseException {
        final int userId = 100;
        Training testTraining = createTraining(userId, userId*2,userId*3,
                "Java",userId,"2024-01-20",15);
        testTraining.setId(userId);

//        serviceFacade.addTraining(testTraining);
//
//        Training training = serviceFacade.getTraining(userId);

//        assertEquals(training.getName(), testTraining.getName());
    }

    @Test
    public void testAdd() {
        final int userId = 100;

        Training testTraining = new Training();
        testTraining.setId(userId);
        testTraining.setName("Java");
        testTraining.setTrainerId(userId);
        testTraining.setTraineeId(userId);
//        serviceFacade.addTraining(testTraining);
//
//        Training training = serviceFacade.getTraining(userId);

//        assertNotNull(training);
//        assertNotNull(training.getName());

    }

    private Training createTraining(Integer id, Integer trainerId, Integer traineeId,
                                    String name, Integer trainingTypeId, String date, Integer duration) throws ParseException {
        Training training = new Training();
//        training.setId(id);
//        training.setTrainerId(trainerId);
//        training.setTraineeId(traineeId);
//        training.setName(name);
//        training.setTrainingTypeId(trainingTypeId);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parsedDate = null;
//        parsedDate = dateFormat.parse(date);
//
//        training.setDate(parsedDate);
//        training.setDuration(duration);

        return training;
    }

}