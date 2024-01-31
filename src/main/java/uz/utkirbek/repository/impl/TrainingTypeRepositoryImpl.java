package uz.utkirbek.repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.TrainingTypeRepository;

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

    @Override
    public Optional<TrainingType> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> root = criteriaQuery.from(TrainingType.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get("name"), name));

        TypedQuery<TrainingType> typedQuery = entityManager.createQuery(criteriaQuery);

        try {
            TrainingType result = typedQuery.getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<TrainingType> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrainingType> criteriaQuery = criteriaBuilder.createQuery(TrainingType.class);
        Root<TrainingType> trainingTypeRoot = criteriaQuery.from(TrainingType.class);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Trainer> trainerRoot = subquery.from(Trainer.class);
        Join<Trainer, User> trainerUserJoin = trainerRoot.join("user", JoinType.LEFT);
        subquery.select(trainerRoot.get("trainingType").get("id"))
                .where(criteriaBuilder.equal(trainerUserJoin.get("username"), username));

        criteriaQuery.select(trainingTypeRoot)
                .where(criteriaBuilder.equal(trainingTypeRoot.get("id"), subquery));

        try {
            TrainingType result = entityManager.createQuery(criteriaQuery).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

}
