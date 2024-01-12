package uz.utkirbek.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.dao.impl.TrainingTypeDaoImpl;
import uz.utkirbek.model.TrainingType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainingTypeDaoImplTest {

    private BaseDao<TrainingType> dao;

    @Before
    public void setUp(){
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
        this.dao=context.getBean(TrainingTypeDaoImpl.class);
    }

    @Test
    public void testGetAll() {
        List<TrainingType> trainingTypes = dao.getAll();
        assertNotNull(trainingTypes);
        assertEquals(0, trainingTypes.size());
    }

    @Test
    public void testAddAndGetOne() throws Exception {
        TrainingType trainingTypeToAdd = createTrainingType(1, "Java Programming");
        dao.add(trainingTypeToAdd);

        List<TrainingType> trainingTypes = dao.getAll();
        assertEquals(1, trainingTypes.size());

        TrainingType retrievedTrainingType = dao.getOne(1);
        assertNotNull(retrievedTrainingType);
        assertTrainingTypeEquals(trainingTypeToAdd, retrievedTrainingType);
    }

    @Test
    public void testUpdate() throws Exception {
        TrainingType initialTrainingType = createTrainingType(1, "Java Basics");
        dao.add(initialTrainingType);

        TrainingType updatedTrainingType = createTrainingType(1, "Advanced Java");
        dao.update(updatedTrainingType);

        TrainingType retrievedTrainingType = dao.getOne(1);
        assertNotNull(retrievedTrainingType);
        assertTrainingTypeEquals(updatedTrainingType, retrievedTrainingType);
    }

    @Test
    public void testDelete() throws Exception {
        TrainingType trainingTypeToDelete = createTrainingType(1, "Java Basics");
        dao.add(trainingTypeToDelete);

        List<TrainingType> initialTrainingTypes = dao.getAll();
        assertEquals(1, initialTrainingTypes.size());

        dao.delete(1);

        List<TrainingType> remainingTrainingTypes = dao.getAll();
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

