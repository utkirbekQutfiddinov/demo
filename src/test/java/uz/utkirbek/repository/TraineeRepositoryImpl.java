package uz.utkirbek.repository;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Trainee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class TraineeRepositoryImpl {

    private TraineeRepository repository;
    private UserRepository userRepository;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.repository=context.getBean(TraineeRepository.class);
        this.userRepository=context.getBean(UserRepository.class);
    }

    @Test
    public void testGetAll() {
        List<Trainee> trainees = repository.readAll();
        assertNotNull(trainees);
    }

    @Test
    public void testAddAndGetOne() throws Exception {
//        final int userId=123456;
//        final int traineeId=1;

//        Trainee traineeToAdd = createTrainee(traineeId, userId, "Tashkent", "1998-01-01");
//        repository.create(traineeToAdd);
//
//        List<Trainee> trainees = repository.readAll();
//        assertEquals(userId, trainees.size());
//
//        Trainee retrievedTrainee = repository.readOne(userId).get();
//        assertNotNull(retrievedTrainee);
//        assertEquals(traineeToAdd.getUserId(), retrievedTrainee.getUserId());
//        assertEquals(traineeToAdd.getAddress(), retrievedTrainee.getAddress());
//        assertEquals(traineeToAdd.getBirthdate(), retrievedTrainee.getBirthdate());
    }

    @Test
    public void testGetOneWhichIsNotExists(){
        Trainee retrievedTrainee = repository.readOne(Integer.MAX_VALUE).get();
        assertNull(retrievedTrainee);
    }

    @Test
    public void testUpdate() throws Exception {
        final int userId=1;
        Trainee initialTrainee = createTrainee(1, userId, "Tashkent", "1998-01-01");
        repository.create(initialTrainee);

        Trainee updatedTrainee = createTrainee(1, userId, "Updated Address", "1998-01-01");
        repository.update(updatedTrainee);

        Trainee retrievedTrainee = repository.readOne(userId).get();
        assertNotNull(retrievedTrainee);
        assertEquals(updatedTrainee.getUserId(), retrievedTrainee.getUserId());
        assertEquals(updatedTrainee.getAddress(), retrievedTrainee.getAddress());
        assertEquals(updatedTrainee.getBirthdate(), retrievedTrainee.getBirthdate());
    }

    @Test
    public void testDelete() throws Exception {
        final int userId=1;
        final int id=112345;
        Trainee traineeToDelete = createTrainee(id, userId, "Tashkent", "1998-01-01");
        repository.create(traineeToDelete);

        Optional<Trainee> optional=repository.readOne(id);

        assertTrue(optional.isPresent());

        repository.delete(optional.get());

        Optional<Trainee> empty=repository.readOne(id);
        assertFalse(empty.isPresent());
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