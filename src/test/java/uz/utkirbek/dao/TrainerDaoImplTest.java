package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.Trainee;
import uz.utkirbek.model.Trainer;

import java.util.List;

import static org.junit.Assert.*;

public class TrainerDaoImplTest {

    private BaseUpdateDao<Trainer> dao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.dao=context.getBean(uz.utkirbek.dao.impl.TrainerDaoImpl.class);
    }

    @Test
    public void getAll() {
        List<Trainer> trainers = dao.getAll();
        assertNotNull(trainers);
        assertEquals(0, trainers.size());
    }

    @Test
    public void addAndGetOne() {
        final int userId=1;
        Trainer trainerToAdd = new Trainer(userId, "Java");
        dao.add(trainerToAdd);

        List<Trainer> trainers = dao.getAll();
        assertEquals(userId, trainers.size());

        Trainer retrievedTrainer = dao.getOne(userId);
        assertNotNull(retrievedTrainer);
        assertEquals(trainerToAdd.getUserId(), retrievedTrainer.getUserId());
        assertEquals(trainerToAdd.getSpecialization(), retrievedTrainer.getSpecialization());
    }

    @Test
    public void getOneWhichIsNotExists(){
        Trainer retrievedTrainer = dao.getOne(Integer.MAX_VALUE);
        assertNull(retrievedTrainer);
    }

    @Test
    public void update() {
        final int userId=1;
        Trainer initialTrainer = new Trainer(userId, "Java");
        dao.add(initialTrainer);

        Trainer updatedTrainer = new Trainer(userId, "Python");
        dao.update(updatedTrainer);

        Trainer retrievedTrainer = dao.getOne(userId);
        assertNotNull(retrievedTrainer);
        assertEquals(updatedTrainer.getUserId(), retrievedTrainer.getUserId());
        assertEquals(updatedTrainer.getSpecialization(), retrievedTrainer.getSpecialization());
    }

}
