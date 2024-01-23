package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.repository.TrainingTypeRepository;
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
        EntityTransaction transaction = entityManager.getTransaction();

        if (item.getName() == null || item.getId() != null) {
            return Optional.empty();
        }

        try {
            transaction.begin();

            entityManager.persist(item);

            return Optional.of(item);

        } finally {
            transaction.commit();
        }
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
}
