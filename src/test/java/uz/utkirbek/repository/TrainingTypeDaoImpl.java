package uz.utkirbek.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.model.TrainingType;

import java.util.List;

import static org.junit.Assert.*;

public class TrainingTypeDaoImpl {

//    private BaseDao<TrainingType> dao;

    @Before
    public void setUp(){
//        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext("uz.utkirbek");
//        this.dao=context.getBean(uz.utkirbek.repository.impl.TrainingTypeDaoImpl.class);
    }

    @Test
    public void testGetAll() {
//        List<TrainingType> trainingTypes = dao.getAll();
//        assertNotNull(trainingTypes);
//        assertEquals(0, trainingTypes.size());
    }

    @Test
    public void testAdd(){
//        int beforeAdding = dao.getAll().size();
//
//        TrainingType trainingTypeToAdd = createTrainingType(1, "Java Programming");
//        dao.add(trainingTypeToAdd);
//
//        int afterAdding = dao.getAll().size();
//
//        assertEquals(beforeAdding, afterAdding-1);

    }

    @Test
    public void testAddAndGetOne(){
//        TrainingType trainingTypeToAdd = createTrainingType(1, "Java Programming");
//        dao.add(trainingTypeToAdd);
//
//        List<TrainingType> trainingTypes = dao.getAll();
//        assertEquals(1, trainingTypes.size());
//
//        TrainingType retrievedTrainingType = dao.getOne(1);
//        assertNotNull(retrievedTrainingType);
//        assertTrainingTypeEquals(trainingTypeToAdd, retrievedTrainingType);
    }

    @Test
    public void testGetOneWhichIsNotExists(){
//        TrainingType retrievedTrainingType = dao.getOne(Integer.MAX_VALUE);
//        assertNull(retrievedTrainingType);
    }

    private void assertTrainingTypeEquals(TrainingType expected, TrainingType actual) {
//        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getName(), actual.getName());
    }
}

