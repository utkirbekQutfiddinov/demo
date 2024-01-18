package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.TrainingType;

import java.util.List;

import static org.junit.Assert.*;

public class TrainingTypeServiceImpl {

//    private ServiceFacade serviceFacade;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
//        serviceFacade = context.getBean(ServiceFacade.class);
    }

    @Test
    public void testGetAll() {

//        for (int i = 0; i < 10; i++) {
//            TrainingType newTrainingType = new TrainingType(i, String.valueOf(i * 456));
//            serviceFacade.addTrainingType(newTrainingType);
//        }
//
//        List<TrainingType> allTrainingTypes = serviceFacade.getAllTrainingTypes();
//
//        assertEquals(10, allTrainingTypes.size());
    }

    @Test
    public void testGetOne() {
        final int userId = 100;
//        TrainingType testTrainingType = new TrainingType(userId, "Onsite");
//        testTrainingType.setId(userId);
//
//        serviceFacade.addTrainingType(testTrainingType);
//
//        TrainingType user = serviceFacade.getTrainingType(userId);
//
//        assertEquals(user.getName(), testTrainingType.getName());
    }

    @Test
    public void testAdd() {
        final int userId = 100;

        TrainingType testTrainingType = new TrainingType();
//        testTrainingType.setName("Onsite");
//        testTrainingType.setId(userId);
//        serviceFacade.addTrainingType(testTrainingType);
//
//        TrainingType trainingType = serviceFacade.getTrainingType(userId);
//
//        assertNotNull(trainingType);
//        assertNotNull(trainingType.getName());

    }

}