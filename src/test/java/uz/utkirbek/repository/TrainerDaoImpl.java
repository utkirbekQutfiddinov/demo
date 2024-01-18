package uz.utkirbek.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Trainer;

import java.util.List;

import static org.junit.Assert.*;

public class TrainerDaoImpl {

    private TrainerRepository repository;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.repository=context.getBean(TrainerRepository.class);
    }

    @Test
    public void testGetAll() {
        List<Trainer> trainers = repository.readAll();
        assertNotNull(trainers);
        assertEquals(0, trainers.size());
    }

    @Test
    public void testAddAndGetOne() {
//        final int userId=1;
//        Trainer trainerToAdd = new Trainer(userId, "Java");
//        repository.create(trainerToAdd);
//
//        List<Trainer> trainers = repository.readAll();
//        assertEquals(userId, trainers.size());
//
//        Trainer retrievedTrainer = repository.readOne(userId).get();
//        assertNotNull(retrievedTrainer);
//        assertEquals(trainerToAdd.getUserId(), retrievedTrainer.getUserId());
//        assertEquals(trainerToAdd.getSpecialization(), retrievedTrainer.getSpecialization());
    }

    @Test
    public void testGetOneWhichIsNotExists(){
        Trainer retrievedTrainer = repository.readOne(Integer.MAX_VALUE).get();
        assertNull(retrievedTrainer);
    }

    @Test
    public void testUpdate() {
//        final int userId=1;
//        Trainer initialTrainer = new Trainer(userId, "Java");
//        initialTrainer.setUserId(userId);
//        initialTrainer.setSpecialization();
//        repository.create(initialTrainer);
//
//        Trainer updatedTrainer = new Trainer(userId, "Python");
//        repository.update(updatedTrainer);
//
//        Trainer retrievedTrainer = repository.readOne(userId).get();
//        assertNotNull(retrievedTrainer);
//        assertEquals(updatedTrainer.getUserId(), retrievedTrainer.getUserId());
//        assertEquals(updatedTrainer.getSpecialization(), retrievedTrainer.getSpecialization());
    }

}
