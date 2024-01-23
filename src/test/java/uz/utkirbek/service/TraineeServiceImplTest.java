package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Trainee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TraineeServiceImplTest {

    private ServiceFacade serviceFacade;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
        serviceFacade = context.getBean(ServiceFacade.class);
    }

    @Test
    public void getAll() {

        for (int i = 0; i < 10; i++) {
            Trainee newTrainee = new Trainee();
            serviceFacade.addTrainee(newTrainee);
        }

        List<Trainee> allTrainees = serviceFacade.getAllTrainees();

        assertEquals(10, allTrainees.size());
    }

    @Test
    public void getOne() {
        final int traineeId = 100;
        Trainee testTrainee = new Trainee();
        testTrainee.setId(traineeId);

        serviceFacade.addTrainee(testTrainee);

        Trainee trainee = serviceFacade.getTrainee(traineeId);

        assertEquals(trainee.getAddress(), testTrainee.getAddress());
    }

    @Test
    public void add() throws ParseException {
        final int traineeId = 100;

        Trainee testTrainee = createTrainee(traineeId, traineeId, "Tashkent", "1998-01-01");
        serviceFacade.addTrainee(testTrainee);

        Trainee trainee = serviceFacade.getTrainee(traineeId);

        assertNotNull(trainee);
        assertNotNull(trainee.getAddress());
        assertNotNull(trainee.getBirthdate());

    }

    @Test
    public void update() throws ParseException {

        final int traineeId = 100;

        Trainee testTrainee = createTrainee(traineeId, traineeId, "Tashkent", "1998-01-01");
        testTrainee.setId(traineeId);

        serviceFacade.addTrainee(testTrainee);

        testTrainee.setAddress("newAddress");

        serviceFacade.updateTrainee(testTrainee);

        assertEquals("newAddress", serviceFacade.getTrainee(traineeId).getAddress());
    }

    @Test
    public void delete() throws ParseException {
        final int traineeId=100;
        Trainee testTrainee = createTrainee(traineeId, traineeId, "Tashkent", "1998-01-01");
        testTrainee.setId(traineeId);

        serviceFacade.addTrainee(testTrainee);

        Trainee trainee1=serviceFacade.getTrainee(traineeId);
        assertNotNull(trainee1);

        serviceFacade.deleteTrainee(traineeId);
        Trainee trainee2=serviceFacade.getTrainee(traineeId);
        assertNull(trainee2);
    }

    private Trainee createTrainee(int id, int traineeId, String address, String birthdate) throws ParseException {
        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setUserId(traineeId);
        trainee.setAddress(address);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedBirthdate = null;
        parsedBirthdate = dateFormat.parse(birthdate);

        trainee.setBirthdate(parsedBirthdate);

        return trainee;
    }

}