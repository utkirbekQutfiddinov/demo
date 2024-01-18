package uz.utkirbek.dao;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.TrainingType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TraineeDaoImpl {

    private BaseDeleteDao<Trainee> dao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.dao=context.getBean(uz.utkirbek.dao.impl.TraineeDaoImpl.class);
    }

    @Test
    public void testGetAll() {
        List<Trainee> trainees = dao.getAll();
        assertNotNull(trainees);
        assertEquals(0, trainees.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        final int userId=1;
        final int traineeId=1;
        Trainee traineeToAdd = createTrainee(traineeId, userId, "Tashkent", "1998-01-01");
        dao.add(traineeToAdd);

        List<Trainee> trainees = dao.getAll();
        assertEquals(userId, trainees.size());

        Trainee retrievedTrainee = dao.getOne(userId);
        assertNotNull(retrievedTrainee);
        assertEquals(traineeToAdd.getUserId(), retrievedTrainee.getUserId());
        assertEquals(traineeToAdd.getAddress(), retrievedTrainee.getAddress());
        assertEquals(traineeToAdd.getBirthdate(), retrievedTrainee.getBirthdate());
    }

    @Test
    public void testGetOneWhichIsNotExists(){
        Trainee retrievedTrainee = dao.getOne(Integer.MAX_VALUE);
        assertNull(retrievedTrainee);
    }

    @Test
    public void testUpdate() throws Exception {
        final int userId=1;
        Trainee initialTrainee = createTrainee(1, userId, "Tashkent", "1998-01-01");
        dao.add(initialTrainee);

        Trainee updatedTrainee = createTrainee(1, userId, "Updated Address", "1998-01-01");
        dao.update(updatedTrainee);

        Trainee retrievedTrainee = dao.getOne(userId);
        assertNotNull(retrievedTrainee);
        assertEquals(updatedTrainee.getUserId(), retrievedTrainee.getUserId());
        assertEquals(updatedTrainee.getAddress(), retrievedTrainee.getAddress());
        assertEquals(updatedTrainee.getBirthdate(), retrievedTrainee.getBirthdate());
    }

    @Test
    public void testDelete() throws Exception {
        final int userId=1;
        Trainee traineeToDelete = createTrainee(1, userId, "Tashkent", "1998-01-01");
        dao.add(traineeToDelete);

        List<Trainee> initialTrainees = dao.getAll();
        assertEquals(userId, initialTrainees.size());

        dao.delete(userId);

        List<Trainee> remainingTrainees = dao.getAll();
        assertEquals(0, remainingTrainees.size());
    }

    private Trainee createTrainee(int id, int userId, String address, String birthdate) throws ParseException {
        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setUserId(userId);
        trainee.setAddress(address);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedBirthdate = dateFormat.parse(birthdate);
        trainee.setBirthdate(parsedBirthdate);

        return trainee;
    }
}