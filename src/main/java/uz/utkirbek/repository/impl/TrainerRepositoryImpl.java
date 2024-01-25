package uz.utkirbek.repository.impl;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.repository.TrainerRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerRepositoryImpl.class);

    private static final String SELECT_ALL = "select u.* from trainers u";
    private static final String SELECT_BY_USERNAME = "select t.* from trainers t" +
            "left join users u on t.user_id=u.id" +
            "where u.username=:username";
    private static final String USERNAME = "username";
    private static final String SELECT_ACTIVE_NOT_ASSIGNED = "select t.* " +
            "from trainers t " +
            "left join users u on u.id=t.user_id" +
            "where count(select * from trainings t1 " +
            "where t1.trainer_id=t.id)=0" +
            "and u.is_active=true";

    private final EntityManager entityManager;

    public TrainerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Trainer> create(Trainer item) {
        EntityTransaction transaction = entityManager.getTransaction();

        if (item.getUser() == null || item.getUser().getId() == 0) {
            LOGGER.trace("Empty parameters");
            return Optional.empty();
        }

        try {
            transaction.begin();
            if (item.getId() == 0) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            return Optional.of(item);
        } finally {
            transaction.commit();
        }

    }

    @Override
    public Optional<Trainer> findById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Trainer trainer = entityManager.find(Trainer.class, id);
            return trainer == null ? Optional.empty() : Optional.of(trainer);
        } finally {
            transaction.commit();
        }
    }

    @Override
    public List<Trainer> findAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Query nativeQuery = entityManager.createNativeQuery(SELECT_ALL);
            return nativeQuery.getResultList();
        } finally {
            transaction.commit();
        }
    }

    @Override
    public Optional<Trainer> update(Trainer item) {
        return create(item);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query nativeQuery = entityManager.createNativeQuery(SELECT_BY_USERNAME);
            nativeQuery.setParameter(USERNAME, username);
            Trainer trainer = (Trainer) nativeQuery.getSingleResult();

            return trainer != null ? Optional.of(trainer) : Optional.empty();
        } finally {
            transaction.commit();
        }

    }

    @Override
    public List<Trainer> getNotAssignedAndActive() {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Query nativeQuery = entityManager.createNativeQuery(SELECT_ACTIVE_NOT_ASSIGNED);
            return nativeQuery.getResultList();
        } finally {
            transaction.commit();
        }


    }
}
