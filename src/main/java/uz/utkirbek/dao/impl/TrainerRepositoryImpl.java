package uz.utkirbek.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.TrainerRepository;
import uz.utkirbek.model.Trainer;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    private final EntityManager entityManager;

    public TrainerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Trainer> create(Trainer item) {
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
    public Optional<Trainer> readOne(Integer key) {
        Trainer trainer = entityManager.find(Trainer.class, key);
        return trainer == null ? Optional.empty() : Optional.of(trainer);
    }

    @Override
    public List<Trainer> readAll() {
        String sql = "select u.* from trainers u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<Trainer> update(Trainer item) {
        return create(item);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        try {
            entityManager.getTransaction().begin();

            String sql = "select t.* from trainers t" +
                    "left join users u on t.user_id=u.id" +
                    "where u.username=:username";
            Query nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("username", username);
            Trainer trainer = (Trainer) nativeQuery.getSingleResult();

            return trainer != null ? Optional.of(trainer) : Optional.empty();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return Optional.empty();
    }
}
