package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import uz.utkirbek.dao.TrainingTypeDao;
import uz.utkirbek.dao.impl.TrainingTypeDaoImpl;
import uz.utkirbek.model.TrainingType;
import uz.utkirbek.storage.StorageBean;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainingTypeDaoImplTest {

    private TrainingTypeDao trainingTypeDao;

    @Before
    public void setUp() {
        StorageBean storageBean = new StorageBean(new HashMap<>());
        trainingTypeDao = new TrainingTypeDaoImpl(storageBean);
    }

    @Test
    public void testGetAll() {
        List<TrainingType> trainingTypes = trainingTypeDao.getAll();
        assertNotNull(trainingTypes);
        assertEquals(0, trainingTypes.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        TrainingType trainingTypeToAdd = createTrainingType(1, "Java Programming");
        trainingTypeDao.add(trainingTypeToAdd);

        List<TrainingType> trainingTypes = trainingTypeDao.getAll();
        assertEquals(1, trainingTypes.size());

        TrainingType retrievedTrainingType = trainingTypeDao.getOne(1);
        assertNotNull(retrievedTrainingType);
        assertTrainingTypeEquals(trainingTypeToAdd, retrievedTrainingType);
    }

    @Test
    public void testUpdate() throws Exception {
        TrainingType initialTrainingType = createTrainingType(1, "Java Basics");
        trainingTypeDao.add(initialTrainingType);

        TrainingType updatedTrainingType = createTrainingType(1, "Advanced Java");
        trainingTypeDao.update(updatedTrainingType);

        TrainingType retrievedTrainingType = trainingTypeDao.getOne(1);
        assertNotNull(retrievedTrainingType);
        assertTrainingTypeEquals(updatedTrainingType, retrievedTrainingType);
    }

    @Test
    public void testDelete() throws Exception {
        TrainingType trainingTypeToDelete = createTrainingType(1, "Java Basics");
        trainingTypeDao.add(trainingTypeToDelete);

        List<TrainingType> initialTrainingTypes = trainingTypeDao.getAll();
        assertEquals(1, initialTrainingTypes.size());

        trainingTypeDao.delete(1);

        List<TrainingType> remainingTrainingTypes = trainingTypeDao.getAll();
        assertEquals(0, remainingTrainingTypes.size());
    }

    private TrainingType createTrainingType(Integer id, String name) {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(id);
        trainingType.setName(name);

        return trainingType;
    }

    private void assertTrainingTypeEquals(TrainingType expected, TrainingType actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }
}

