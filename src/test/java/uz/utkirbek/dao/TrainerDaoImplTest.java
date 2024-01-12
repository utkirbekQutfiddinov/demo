package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.dao.impl.TrainerDaoImpl;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainerDaoImplTest {

    private BaseDao<Trainer> dao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.dao=context.getBean(TrainerDaoImpl.class);
    }

    @Test
    public void testGetAll() {
        List<Trainer> trainers = dao.getAll();
        assertNotNull(trainers);
        assertEquals(0, trainers.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
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
    public void testUpdate() throws Exception {
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

    @Test
    public void testDelete() throws Exception {
        final int userId=1;
        Trainer trainerToDelete = new Trainer(userId, "Java");
        dao.add(trainerToDelete);

        List<Trainer> initialTrainers = dao.getAll();
        assertEquals(userId, initialTrainers.size());

        dao.delete(userId);

        List<Trainer> remainingTrainers = dao.getAll();
        assertEquals(0, remainingTrainers.size());
    }
}
