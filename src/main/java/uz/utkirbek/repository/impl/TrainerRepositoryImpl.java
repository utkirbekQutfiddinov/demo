package uz.utkirbek.repository.impl;

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
    private final EntityManager entityManager;

    public TrainerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Trainer> create(Trainer item) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (item.getUserId() == null || item.getUser() == null) {
            transaction.commit();
            return Optional.empty();
        }

        try {
            if (item.getId() == null) {
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
    public Optional<Trainer> readOne(Integer key) {
        EntityTransaction transaction= entityManager.getTransaction();
        transaction.begin();
        try{
            Trainer trainer = entityManager.find(Trainer.class, key);
            return trainer == null ? Optional.empty() : Optional.of(trainer);
        }finally {
            transaction.commit();
        }
    }

    @Override
    public List<Trainer> readAll() {
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();
        try{
            String sql = "select u.* from trainers u";
            Query nativeQuery = entityManager.createNativeQuery(sql);
            return nativeQuery.getResultList();
        }finally {
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

            String sql = "select t.* from trainers t" +
                    "left join users u on t.user_id=u.id" +
                    "where u.username=:username";
            Query nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("username", username);
            Trainer trainer = (Trainer) nativeQuery.getSingleResult();

            return trainer != null ? Optional.of(trainer) : Optional.empty();
        }  finally {
            transaction.commit();
        }

    }

    @Override
    public List<Trainer> getNotAssignedAndActive() {
        EntityTransaction transaction= entityManager.getTransaction();

        try{
            transaction.begin();
            String sql = "select t.* " +
                    "from trainers t " +
                    "left join users u on u.id=t.user_id" +
                    "where count(select * from trainings t1 " +
                    "where t1.trainer_id=t.id)=0" +
                    "and u.is_active=true";
            Query nativeQuery = entityManager.createNativeQuery(sql);
            return nativeQuery.getResultList();
        }finally {
            transaction.commit();
        }


    }
}
