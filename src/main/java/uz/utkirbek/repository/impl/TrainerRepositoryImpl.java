package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.TrainerRepository;
import uz.utkirbek.repository.TrainingTypeRepository;
import uz.utkirbek.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerRepositoryImpl.class);

    private static final String SELECT_ALL = "select u.* from trainers u";
    private static final String SELECT_TRAINER_BY_TRAINING_ID = "select t2.* from trainings t" +
            " left join trainers t2 on t.trainer_id = t2.id" +
            " where t.id=:trainingId";

    private final TrainingTypeRepository trainingTypeRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public TrainerRepositoryImpl(TrainingTypeRepository trainingTypeRepository, UserRepository userRepository, EntityManager entityManager) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Trainer> create(TrainerDto item) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            User user = new User(item.getFirstName(), item.getLastName());
            user.setUsername(generateUsername(item.getFirstName(), item.getLastName()));
            user.setPassword(generatePassword());

            Optional<TrainingType> trainingType = trainingTypeRepository.findByName(item.getSpecialization().getName());

            if (!trainingType.isPresent()) {
                return Optional.empty();
            }
            Trainer trainer = new Trainer();
            trainer.setTrainingType(trainingType.get());
            trainer.setUser(user);
            entityManager.persist(trainer);

            return Optional.ofNullable(trainer);
        } catch (Exception e) {
            return Optional.empty();
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
            return trainer == null ? Optional.empty() : Optional.ofNullable(trainer);
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
        item = entityManager.merge(item);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trainer> criteriaQuery = criteriaBuilder.createQuery(Trainer.class);
        Root<Trainer> trainerRoot = criteriaQuery.from(Trainer.class);

        Join<Trainer, User> userJoin = trainerRoot.join("user", JoinType.LEFT);


        Predicate usernamePredicate = criteriaBuilder.equal(userJoin.get("username"), username);
        criteriaQuery.where(usernamePredicate);

        try {
            Trainer trainer = entityManager.createQuery(criteriaQuery).getSingleResult();
            return Optional.ofNullable(trainer);
        } catch (NoResultException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Trainer findByTrainingId(Integer trainingId) {
        try {

            Query nativeQuery = entityManager.createNativeQuery(SELECT_TRAINER_BY_TRAINING_ID);
            nativeQuery.setParameter("trainingId", trainingId);
            Object[] result = (Object[]) nativeQuery.getSingleResult();

            Trainer singleResult = new Trainer();
            singleResult.setId((Integer) result[0]);

            Optional<TrainingType> type = trainingTypeRepository.findById((Integer) result[1]);

            singleResult.setTrainingType(type.orElse(null));

            Optional<User> userOptional = userRepository.findById((Integer) result[2]);
            singleResult.setUser(userOptional.orElse(null));

            return singleResult;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private String generateUsername(String firstname, String lastname) {
        String baseUsername = firstname + lastname;
        String username = baseUsername;

        int serialNumber = 1;
        while (usernameExists(username)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        return username;
    }

    private boolean usernameExists(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }


}
