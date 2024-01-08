package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import uz.utkirbek.dao.TrainingDao;
import uz.utkirbek.dao.impl.TrainingDaoImpl;
import uz.utkirbek.model.Training;
import uz.utkirbek.storage.StorageBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainingDaoImplTest {

    private TrainingDao trainingDao;

    @Before
    public void setUp() {
        StorageBean storageBean = new StorageBean(new HashMap<>());
        trainingDao = new TrainingDaoImpl(storageBean);
    }

    @Test
    public void testGetAll() {
        List<Training> trainings = trainingDao.getAll();
        assertNotNull(trainings);
        assertEquals(0, trainings.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        Training trainingToAdd = createTraining(1, 1, 2, "Java Basics", 1, new Date(), 60);
        trainingDao.add(trainingToAdd);

        List<Training> trainings = trainingDao.getAll();
        assertEquals(1, trainings.size());

        Training retrievedTraining = trainingDao.getOne(1);
        assertNotNull(retrievedTraining);
        assertTrainingEquals(trainingToAdd, retrievedTraining);
    }

    @Test
    public void testUpdate() throws Exception {
        Training initialTraining = createTraining(1, 1, 2, "Java Basics", 1, new Date(), 60);
        trainingDao.add(initialTraining);

        Training updatedTraining = createTraining(1, 2, 3, "Advanced Java", 2, new Date(), 90);
        trainingDao.update(updatedTraining);

        Training retrievedTraining = trainingDao.getOne(1);
        assertNotNull(retrievedTraining);
        assertTrainingEquals(updatedTraining, retrievedTraining);
    }

    @Test
    public void testDelete() throws Exception {
        Training trainingToDelete = createTraining(1, 1, 2, "Java Basics", 1, new Date(), 60);
        trainingDao.add(trainingToDelete);

        List<Training> initialTrainings = trainingDao.getAll();
        assertEquals(1, initialTrainings.size());

        trainingDao.delete(1);

        List<Training> remainingTrainings = trainingDao.getAll();
        assertEquals(0, remainingTrainings.size());
    }

    private Training createTraining(Integer id, Integer trainerId, Integer traineeId,
                                    String name, Integer trainingTypeId, Date date, Integer duration) {
        Training training = new Training();
        training.setId(id);
        training.setTrainerId(trainerId);
        training.setTraineeId(traineeId);
        training.setName(name);
        training.setTrainingTypeId(trainingTypeId);
        training.setDate(date);
        training.setDuration(duration);

        return training;
    }

    private void assertTrainingEquals(Training expected, Training actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTrainerId(), actual.getTrainerId());
        assertEquals(expected.getTraineeId(), actual.getTraineeId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getTrainingTypeId(), actual.getTrainingTypeId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getDuration(), actual.getDuration());
    }
}