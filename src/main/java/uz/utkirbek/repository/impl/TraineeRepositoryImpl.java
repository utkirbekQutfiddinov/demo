package uz.utkirbek.repository.impl;

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
    private final EntityManager entityManager;

    public TraineeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Trainee> create(Trainee item) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

        if (item.getUser().getId() == 0) {
            return Optional.empty();
        }

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
    public Optional<Trainee> readOne(int id) {
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
    public List<Trainee> readAll() {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            String sql = "select u.* from trainees u";
            Query nativeQuery = entityManager.createNativeQuery(sql);
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

            String sql = "select t.* from trainees t" +
                    "left join users u on t.user_id=u.id" +
                    "where u.username=:username";
            Query nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("username", username);
            Trainee trainee = (Trainee) nativeQuery.getSingleResult();

            return trainee != null ? Optional.of(trainee) : Optional.empty();
        } finally {
            transaction.commit();
        }

    }
}
