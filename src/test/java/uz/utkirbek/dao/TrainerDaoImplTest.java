package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import uz.utkirbek.dao.impl.TrainerDaoImpl;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.storage.StorageBean;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainerDaoImplTest {

    private TrainerDao trainerDao;

    @Before
    public void setUp() {
        StorageBean storageBean = new StorageBean(new HashMap<>());
        trainerDao = new TrainerDaoImpl(storageBean);
    }

    @Test
    public void testGetAll() {
        List<Trainer> trainers = trainerDao.getAll();
        assertNotNull(trainers);
        assertEquals(0, trainers.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        Trainer trainerToAdd = createTrainer(1, "John Doe", "Java");
        trainerDao.add(trainerToAdd);

        List<Trainer> trainers = trainerDao.getAll();
        assertEquals(1, trainers.size());

        Trainer retrievedTrainer = trainerDao.getOne(1);
        assertNotNull(retrievedTrainer);
        assertEquals(trainerToAdd.getUserId(), retrievedTrainer.getUserId());
        assertEquals(trainerToAdd.getSpecialization(), retrievedTrainer.getSpecialization());
    }

    @Test
    public void testUpdate() throws Exception {
        Trainer initialTrainer = createTrainer(1, "John Doe", "Java");
        trainerDao.add(initialTrainer);

        Trainer updatedTrainer = createTrainer(1, "Updated Name", "Python");
        trainerDao.update(updatedTrainer);

        Trainer retrievedTrainer = trainerDao.getOne(1);
        assertNotNull(retrievedTrainer);
        assertEquals(updatedTrainer.getUserId(), retrievedTrainer.getUserId());
        assertEquals(updatedTrainer.getSpecialization(), retrievedTrainer.getSpecialization());
    }

    @Test
    public void testDelete() throws Exception {
        Trainer trainerToDelete = createTrainer(1, "John Doe", "Java");
        trainerDao.add(trainerToDelete);

        List<Trainer> initialTrainers = trainerDao.getAll();
        assertEquals(1, initialTrainers.size());

        trainerDao.delete(1);

        List<Trainer> remainingTrainers = trainerDao.getAll();
        assertEquals(0, remainingTrainers.size());
    }

    private Trainer createTrainer(Integer id, String name, String specialization) {
        Trainer trainer = new Trainer();
        trainer.setId(id);
        trainer.setUserId(id);
        trainer.setSpecialization(specialization);

        return trainer;
    }
}
