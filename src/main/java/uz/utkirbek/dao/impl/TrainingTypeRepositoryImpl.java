package uz.utkirbek.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainingTypeRepository;
import uz.utkirbek.model.TrainingType;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {
    private final EntityManager entityManager;

    public TrainingTypeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<TrainingType> create(TrainingType item) {
        try {
            entityManager.getTransaction().begin();

            if (item.getId() == null) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }

            entityManager.getTransaction().commit();

            return Optional.of(item);

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<TrainingType> readOne(Integer key) {
        TrainingType type = entityManager.find(TrainingType.class, key);
        return type == null ? Optional.empty() : Optional.of(type);
    }

    @Override
    public List<TrainingType> readAll() {
        String sql = "select u.* from training_types u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<TrainingType> update(TrainingType item) {
        return create(item);
    }

    @Override
    public void delete(TrainingType item) {
        entityManager.getTransaction().begin();

        if (entityManager.contains(item)) {
            entityManager.remove(item);
        }
        
        entityManager.getTransaction().commit();
    }
}
