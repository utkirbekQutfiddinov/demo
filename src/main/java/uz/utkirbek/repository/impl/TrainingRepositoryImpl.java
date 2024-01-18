package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.repository.TrainingRepository;

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
        EntityTransaction transaction=entityManager.getTransaction();

        if (item.getName() == null || item.getTrainingDate() == null || item.getDuration() == null) {
            return Optional.empty();
        }

        try {
            transaction.begin();
            if (item.getId() == null) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            return Optional.of(item);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            transaction.commit();
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
    public Optional<Boolean> updateTrainer(Integer trainingId, Trainer trainer) {
        EntityTransaction transaction=entityManager.getTransaction();

        transaction.begin();
        Optional<Training> trainerOptional = readOne(trainingId);

        if (!trainerOptional.isPresent()) {
            transaction.rollback();
            return Optional.empty();
        }

        Training training = trainerOptional.get();
        training.setTrainer(trainer);
        training.setTrainerId(trainer.getId());
        entityManager.flush();
        transaction.commit();
        return Optional.of(true);
    }

    @Override
    public List<Training> getByUsernameAndCriteria(String username) {
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cr = cb.createQuery(Training.class);
        Root<Training> root = cr.from(Training.class);


        List<Training> trainings=entityManager.createQuery(
          cr.select(root)
                .where(cb.equal(root.get("username"),username))
        ).getResultList();

        return trainings;
    }
}
