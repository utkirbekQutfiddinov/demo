package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Trainer;

import java.util.List;

import static org.junit.Assert.*;

public class TrainerServiceImplTest {

    private ServiceFacade serviceFacade;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
        serviceFacade = context.getBean(ServiceFacade.class);
    }

    @Test
    public void testGetAll() {

        for (int i = 0; i < 10; i++) {
            Trainer newTrainer = new Trainer(i, String.valueOf(i * 123));
            serviceFacade.addTrainer(newTrainer);
        }

        List<Trainer> allTrainers = serviceFacade.getAllTrainers();

        assertEquals(10, allTrainers.size());
    }

    @Test
    public void testGetOne() {
        final int userId = 100;
        Trainer testTrainer = new Trainer(userId, "Java");
        testTrainer.setId(userId);

        serviceFacade.addTrainer(testTrainer);

        Trainer trainer= serviceFacade.getTrainer(userId);

        assertEquals(trainer.getSpecialization(), testTrainer.getSpecialization());
    }

    @Test
    public void testAdd() {
        final int userId = 100;

        Trainer testTrainer = new Trainer();
        testTrainer.setId(userId);
        testTrainer.setUserId(userId);
        testTrainer.setSpecialization("Java");
        serviceFacade.addTrainer(testTrainer);

        Trainer trainer= serviceFacade.getTrainer(userId);

        assertNotNull(trainer);
        assertNotNull(trainer.getSpecialization());

    }

    @Test
    public void testUpdate() {

        final int userId = 100;

        Trainer testTrainer = new Trainer(userId, "Java");
        testTrainer.setId(userId);

        serviceFacade.addTrainer(testTrainer);

        testTrainer.setSpecialization("newSpec");

        serviceFacade.updateTrainer(testTrainer);

        assertEquals("newSpec", serviceFacade.getTrainer(userId).getSpecialization());
    }

}