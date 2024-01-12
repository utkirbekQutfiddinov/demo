package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.dao.impl.TrainingDaoImpl;
import uz.utkirbek.model.Training;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainingDaoImplTest {

    private BaseDao<Training> dao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.dao=context.getBean(TrainingDaoImpl.class);
    }

    @Test
    public void testGetAll() {
        List<Training> trainings = dao.getAll();
        assertNotNull(trainings);
        assertEquals(0, trainings.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        Training trainingToAdd = createTraining(1, 1, 2, "Java Basics", 1, new Date(), 60);
        dao.add(trainingToAdd);

        List<Training> trainings = dao.getAll();
        assertEquals(1, trainings.size());

        Training retrievedTraining = dao.getOne(1);
        assertNotNull(retrievedTraining);
        assertTrainingEquals(trainingToAdd, retrievedTraining);
    }

    @Test
    public void testUpdate() throws Exception {
        Training initialTraining = createTraining(1, 1, 2, "Java Basics", 1, new Date(), 60);
        dao.add(initialTraining);

        Training updatedTraining = createTraining(1, 2, 3, "Advanced Java", 2, new Date(), 90);
        dao.update(updatedTraining);

        Training retrievedTraining = dao.getOne(1);
        assertNotNull(retrievedTraining);
        assertTrainingEquals(updatedTraining, retrievedTraining);
    }

    @Test
    public void testDelete() throws Exception {
        Training trainingToDelete = createTraining(1, 1, 2, "Java Basics", 1, new Date(), 60);
        dao.add(trainingToDelete);

        List<Training> initialTrainings = dao.getAll();
        assertEquals(1, initialTrainings.size());

        dao.delete(1);

        List<Training> remainingTrainings = dao.getAll();
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