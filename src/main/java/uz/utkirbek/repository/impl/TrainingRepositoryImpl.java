package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.Trainer;
import uz.utkirbek.model.Training;
import uz.utkirbek.model.User;
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
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        if (item.getName() == null || item.getTrainingDate() == null || item.getDuration() == null) {
            transaction.rollback();
            return Optional.empty();
        }

        try {
            if (item.getId() == 0) {
                entityManager.persist(item);
            } else {
                transaction.rollback();
                return Optional.empty();
            }
            return Optional.of(item);
        } finally {
            transaction.commit();
        }

    }

    @Override
    public Optional<Training> findById(int id) {
        Training training = entityManager.find(Training.class, id);
        return training == null ? Optional.empty() : Optional.of(training);
    }

    @Override
    public List<Training> findAll() {
        String sql = "select u.* from trainings u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<Boolean> updateTrainer(Integer trainingId, Trainer trainer) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Optional<Training> trainerOptional = findById(trainingId);

        if (!trainerOptional.isPresent()) {
            transaction.rollback();
            return Optional.empty();
        }

        Training training = trainerOptional.get();
        training.setTrainer(trainer);
        entityManager.flush();
        transaction.commit();
        return Optional.of(true);
    }

    @Override
    public List<Training> getByUsernameAndCriteria(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> cr = cb.createQuery(Training.class);
        Root<Training> root = cr.from(Training.class);


        List<Training> trainings = entityManager.createQuery(
                cr.select(root)
                        .where(cb.equal(root.get("username"), username))
        ).getResultList();

        return trainings;
    }
}
