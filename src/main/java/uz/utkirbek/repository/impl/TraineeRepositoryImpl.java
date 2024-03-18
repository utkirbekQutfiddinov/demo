package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.entity.*;
import uz.utkirbek.model.response.TraineeTrainerResponse;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainingTypeRepository;
import uz.utkirbek.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeRepositoryImpl implements TraineeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeRepositoryImpl.class);
    private static final String SELECT_ALL = "select u.* from trainees u";
    private static final String SELECT_TRAINEE_BY_TRAINING_ID = "select t2.* from trainings t" +
            " left join trainees t2 on t.trainee_id = t2.id" +
            " where t.id=:trainingId";
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TrainingTypeRepository trainingTypeRepository;


    public TraineeRepositoryImpl(EntityManager entityManager, UserRepository userRepository, PasswordEncoder passwordEncoder, TrainingTypeRepository trainingTypeRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.trainingTypeRepository = trainingTypeRepository;
    }


    @Override
    public Optional<Trainee> create(TraineeDto item) {
        EntityTransaction transaction = entityManager.getTransaction();

        if (item.getFirstName() == null || item.getLastName() == null) {
            return Optional.empty();
        }

        try {
            transaction.begin();
            User user = new User(item.getFirstName(), item.getLastName());
            user.setUsername(generateUsername(item.getFirstName(), item.getLastName()));
            String rawPassword = generateRandomText();
            String passwordSalt = generateRandomText();
            user.setPasswordSalt(passwordSalt);
            user.setPassword(passwordEncoder.encode(passwordSalt + rawPassword));
            user.setActive(true);
            user.setRawPassword(rawPassword);

            Trainee trainee = new Trainee(user, item.getAddress(), item.getBirthDate());

            entityManager.persist(trainee);
            return Optional.ofNullable(trainee);
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return Optional.empty();
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
            return trainee == null ? Optional.empty() : Optional.ofNullable(trainee);
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return Optional.empty();
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
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            transaction.commit();
        }
    }

    @Override
    public Optional<Trainee> update(Trainee item) {
        try {
            User user = item.getUser();
            user.setPassword(passwordEncoder.encode(user.getPasswordSalt() + user.getPassword()));
            item = entityManager.merge(item);
            return Optional.ofNullable(item);
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Trainee item) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            if (entityManager.contains(item)) {
                entityManager.remove(item);
            }
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return false;
        } finally {
            transaction.commit();
        }
        return true;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Trainee> criteriaQuery = criteriaBuilder.createQuery(Trainee.class);
            Root<Trainee> traineeRoot = criteriaQuery.from(Trainee.class);

            Join<Trainee, User> userJoin = traineeRoot.join("user");

            criteriaQuery.where(criteriaBuilder.equal(userJoin.get("username"), username));

            TypedQuery<Trainee> typedQuery = entityManager.createQuery(criteriaQuery);

            Trainee trainee = typedQuery.getSingleResult();
            return Optional.ofNullable(trainee);
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<TraineeTrainerResponse> getNotAssignedActiveTrainers(String username) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TraineeTrainerResponse> criteriaQuery = criteriaBuilder.createQuery(TraineeTrainerResponse.class);
            Root<Trainer> trainerRoot = criteriaQuery.from(Trainer.class);

            Join<Trainer, User> userJoin = trainerRoot.join("user", JoinType.LEFT);

            Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
            Root<Training> trainingRoot = subquery.from(Training.class);
            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee", JoinType.LEFT);
            Join<Trainee, User> traineeUserJoin = traineeJoin.join("user", JoinType.LEFT);
            subquery.select(trainingRoot.get("trainer").get("id"))
                    .where(criteriaBuilder.equal(traineeUserJoin.get("username"), "trainee"));

            criteriaQuery.select(criteriaBuilder.construct(
                            TraineeTrainerResponse.class,
                            userJoin.get("username"),
                            userJoin.get("firstname"),
                            userJoin.get("lastname")
                    ))
                    .where(
                            criteriaBuilder.isTrue(userJoin.get("isActive")),
                            criteriaBuilder.not(trainerRoot.get("id").in(subquery))
                    );

            TypedQuery<TraineeTrainerResponse> typedQuery = entityManager.createQuery(criteriaQuery);
            List<TraineeTrainerResponse> result = typedQuery.getResultList();

            for (TraineeTrainerResponse item : result) {
                Optional<TrainingType> typeOptional = trainingTypeRepository.findByUsername(item.getUsername());
                typeOptional.ifPresent(item::setSpecialization);
            }

            return result;
        } catch (Exception e) {
            LOGGER.error("Error on: " + e.getMessage());
            return Collections.emptyList();
        }

    }

    @Override
    public Trainee findByTrainingId(Integer trainingId) {
        try {

            Query nativeQuery = entityManager.createNativeQuery(SELECT_TRAINEE_BY_TRAINING_ID);
            nativeQuery.setParameter("trainingId", trainingId);

            Object[] result = (Object[]) nativeQuery.getSingleResult();

            Trainee singleResult = new Trainee();
            singleResult.setId((Integer) result[0]);
            singleResult.setAddress((String) result[1]);

            Optional<User> userOptional = userRepository.findById((Integer) result[3]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            singleResult.setUser(userOptional.orElse(null));
            singleResult.setBirthdate(sdf.parse(result[2].toString()));

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

    private String generateRandomText() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
