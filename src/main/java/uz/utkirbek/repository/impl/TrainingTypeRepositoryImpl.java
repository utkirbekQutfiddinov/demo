package uz.utkirbek.repository.impl;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeRepositoryImpl.class);

    private static final String SELECT_ALL = "select u.* from training_types u";
    private final EntityManager entityManager;

    public TrainingTypeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<TrainingType> create(TrainingType item) {
        EntityTransaction transaction = entityManager.getTransaction();

        if (item.getName() == null || item.getId() != 0) {
            LOGGER.trace("Empty parameters");
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
    public Optional<TrainingType> findById(int id) {
        TrainingType type = entityManager.find(TrainingType.class, id);
        return type == null ? Optional.empty() : Optional.of(type);
    }

    @Override
    public List<TrainingType> findAll() {
        Query nativeQuery = entityManager.createNativeQuery(SELECT_ALL);
        return nativeQuery.getResultList();
    }
}
