package uz.utkirbek.dao;


import org.junit.Before;
import org.junit.Test;
import uz.utkirbek.dao.impl.TraineeDaoImpl;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.storage.StorageBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TraineeDaoImplTest {

    private TraineeDao traineeDao;

    @Before
    public void setUp() {
        StorageBean storageBean = new StorageBean(new HashMap<>());
        traineeDao = new TraineeDaoImpl(storageBean);
    }

    @Test
    public void testGetAll() {
        List<Trainee> trainees = traineeDao.getAll();
        assertNotNull(trainees);
        assertEquals(0, trainees.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        Trainee traineeToAdd = createTrainee(1, "Utkirbek", "Tashkent", "1998-01-01");
        traineeDao.add(traineeToAdd);

        List<Trainee> trainees = traineeDao.getAll();
        assertEquals(1, trainees.size());

        Trainee retrievedTrainee = traineeDao.getOne(1);
        assertNotNull(retrievedTrainee);
        assertEquals(traineeToAdd.getUserId(), retrievedTrainee.getUserId());
        assertEquals(traineeToAdd.getAddress(), retrievedTrainee.getAddress());
        assertEquals(traineeToAdd.getBirthdate(), retrievedTrainee.getBirthdate());
    }

    @Test
    public void testUpdate() throws Exception {
        Trainee initialTrainee = createTrainee(1, "Utkirbek", "Tashkent", "1998-01-01");
        traineeDao.add(initialTrainee);

        Trainee updatedTrainee = createTrainee(1, "Updated Name", "Updated Address", "1998-01-01");
        traineeDao.update(updatedTrainee);

        Trainee retrievedTrainee = traineeDao.getOne(1);
        assertNotNull(retrievedTrainee);
        assertEquals(updatedTrainee.getUserId(), retrievedTrainee.getUserId());
        assertEquals(updatedTrainee.getAddress(), retrievedTrainee.getAddress());
        assertEquals(updatedTrainee.getBirthdate(), retrievedTrainee.getBirthdate());
    }

    @Test
    public void testDelete() throws Exception {
        Trainee traineeToDelete = createTrainee(1, "Utkirbek", "Tashkent", "1998-01-01");
        traineeDao.add(traineeToDelete);

        List<Trainee> initialTrainees = traineeDao.getAll();
        assertEquals(1, initialTrainees.size());

        traineeDao.delete(1);

        List<Trainee> remainingTrainees = traineeDao.getAll();
        assertEquals(0, remainingTrainees.size());
    }

    private Trainee createTrainee(Integer id, String name, String address, String birthdate) throws ParseException {
        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setUserId(id);
        trainee.setAddress(address);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedBirthdate = dateFormat.parse(birthdate);
        trainee.setBirthdate(parsedBirthdate);

        return trainee;
    }
}