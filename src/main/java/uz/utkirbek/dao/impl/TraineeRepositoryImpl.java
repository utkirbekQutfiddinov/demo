package uz.utkirbek.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TraineeRepository;
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
    public Optional<Trainee> readOne(Integer key) {
        Trainee trainee = entityManager.find(Trainee.class, key);
        return trainee == null ? Optional.empty() : Optional.of(trainee);
    }

    @Override
    public List<Trainee> readAll() {
        String sql="select u.* from trainees u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<Trainee> update(Trainee item) {
        return create(item);
    }

    @Override
    public void delete(Trainee item) {
        entityManager.getTransaction().begin();

        if (entityManager.contains(item)) {
            entityManager.remove(item);
        }
        entityManager.getTransaction().commit();

    }
}
