package uz.utkirbek.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingRepository;
import uz.utkirbek.model.Training;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
    private final EntityManager entityManager;

    public TrainingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Training> create(Training item) {
        try {
            entityManager.getTransaction().begin();
            if (item.getId() == null) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            return Optional.of(item);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Training> readOne(Integer key) {
        Training training = entityManager.find(Training.class, key);
        return training == null ? Optional.empty() : Optional.of(training);
    }

    @Override
    public List<Training> readAll() {
        String sql = "select u.* from trainings u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<Training> update(Training item) {
        return create(item);
    }

    @Override
    public void delete(Training item) {
        try {
            entityManager.getTransaction().begin();

            if (entityManager.contains(item)) {
                entityManager.remove(item);
            }
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
