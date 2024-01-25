package uz.utkirbek.repository.impl;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.model.Trainee;

import java.util.List;
import java.util.Optional;

@Repository
public class TraineeRepositoryImpl implements TraineeRepository {
    static final Logger LOGGER = LoggerFactory.getLogger(TraineeRepositoryImpl.class);

    private final EntityManager entityManager;
    private final String SELECT_ALL = "select u.* from trainees u";
    private final String SELECT_BY_USERNAME = "select t.* from trainees t" +
            "left join users u on t.user_id=u.id" +
            "where u.username=:username";
    private final String USERNAME = "username";

    public TraineeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Trainee> create(Trainee item) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            if (item.getUser().getId() == 0) {
                LOGGER.trace("Empty parameters");
                return Optional.empty();
            }
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
    public Optional<Trainee> findById(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Trainee trainee = entityManager.find(Trainee.class, id);
            return trainee == null ? Optional.empty() : Optional.of(trainee);
        } finally {
            transaction.commit();
        }
    }

    @Override
    public List<Trainee> findAll() {
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
    public Optional<Trainee> update(Trainee item) {
        return create(item);
    }

    @Override
    public void delete(Trainee item) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            if (entityManager.contains(item)) {
                entityManager.remove(item);
            }
        } finally {
            transaction.commit();
        }
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query nativeQuery = entityManager.createNativeQuery(SELECT_BY_USERNAME);
            nativeQuery.setParameter(USERNAME, username);
            Trainee trainee = (Trainee) nativeQuery.getSingleResult();

            return trainee != null ? Optional.of(trainee) : Optional.empty();
        } finally {
            transaction.commit();
        }

    }
}
