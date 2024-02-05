package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.*;
import uz.utkirbek.model.response.TrainingResponse;
import uz.utkirbek.repository.TraineeRepository;
import uz.utkirbek.repository.TrainerRepository;
import uz.utkirbek.repository.TrainingRepository;
import uz.utkirbek.repository.TrainingTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingRepositoryImpl.class);
    private static final String SELECT_ALL = "select u.* from trainings u";
    private final EntityManager entityManager;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingRepositoryImpl(EntityManager entityManager, TrainerRepository trainerRepository, TraineeRepository traineeRepository, TrainingTypeRepository trainingTypeRepository) {
        this.entityManager = entityManager;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }


    @Override
    public Optional<Training> create(TrainingDto item) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Optional<Trainer> trainerOptional = trainerRepository.findByUsername(item.getTrainerUsername());

            if (!trainerOptional.isPresent()) {
                return Optional.empty();
            }
            Trainer trainer = trainerOptional.get();

            Optional<Trainee> traineeOptional = traineeRepository.findByUsername(item.getTraineeUsername());

            if (!traineeOptional.isPresent()) {
                return Optional.empty();
            }
            Trainee trainee = traineeOptional.get();

            Training newTraining = new Training();
            newTraining.setTrainingDate(item.getDate());
            newTraining.setTrainer(trainer);
            newTraining.setTrainee(trainee);
            newTraining.setDuration(item.getDuration());
            newTraining.setName(item.getName());

            entityManager.persist(newTraining);

            return Optional.ofNullable(newTraining);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        } finally {
            transaction.commit();
        }

    }

    @Override
    public Optional<Training> findById(int id) {
        Training training = entityManager.find(Training.class, id);
        return training == null ? Optional.empty() : Optional.ofNullable(training);
    }

    @Override
    public List<Training> findAll() {
        Query nativeQuery = entityManager.createNativeQuery(SELECT_ALL);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<Boolean> updateTrainer(Integer trainingId, String trainerUsername) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();
            Training training = entityManager.find(Training.class, trainingId);

            if (training == null) {
                transaction.rollback();
                return Optional.empty();
            }

            Optional<Trainer> trainerOptional = trainerRepository.findByUsername(trainerUsername);

            if (!trainerOptional.isPresent()) {
                transaction.rollback();
                return Optional.empty();
            }

            Trainer trainer = trainerOptional.get();

            training.setTrainer(trainer);

            entityManager.merge(training);
            transaction.commit();
            return Optional.of(true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Optional.of(false);
        }
    }

    @Override
    public List<TrainingResponse> getByCriteria(TrainingFiltersDto filter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> criteriaQuery = criteriaBuilder.createQuery(Training.class);
        Root<Training> trainingRoot = criteriaQuery.from(Training.class);

        Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer", JoinType.LEFT);
        Join<Trainer, TrainingType> trainingTypeJoin = trainerJoin.join("trainingType", JoinType.LEFT);
        Join<Trainer, User> userJoin = trainerJoin.join("user", JoinType.LEFT);
        Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee", JoinType.LEFT);
        Join<Trainee, User> traineeUserJoin = traineeJoin.join("user", JoinType.LEFT);

        criteriaQuery.multiselect(
                trainingRoot.get("id"),
                trainingRoot.get("name"),
                trainingRoot.get("duration"),
                trainingRoot.get("trainingDate")
        );

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getTraineeUsername() != null) {
            predicates.add(criteriaBuilder.equal(traineeUserJoin.get("username"), filter.getTraineeUsername()));
        }

        if (filter.getPeriodFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), filter.getPeriodFrom()));
        }

        if (filter.getPeriodTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(trainingRoot.get("trainingDate"), filter.getPeriodTo()));
        }

        if (filter.getTrainerUsername() != null) {
            predicates.add(criteriaBuilder.equal(userJoin.get("username"), filter.getTrainerUsername()));
        }

        if (filter.getTrainingType() != null) {
            predicates.add(criteriaBuilder.equal(trainingTypeJoin.get("name"), filter.getTrainingType()));
        }


        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Training> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Training> resultList = typedQuery.getResultList();

        List<TrainingResponse> finalList = new ArrayList<>();
        for (Training training : resultList) {
            TrainingResponse resp = new TrainingResponse();
            resp.setDate(training.getTrainingDate());
            resp.setName(training.getName());
            resp.setDuration(training.getDuration());

            Trainee trainee = traineeRepository.findByTrainingId(training.getId());
            if (trainee != null && trainee.getUser() != null) {
                resp.setTraineeUsername(trainee.getUser().getUsername());
            }

            Trainer trainer = trainerRepository.findByTrainingId(training.getId());
            if (trainer != null && trainer.getUser() != null) {
                resp.setTrainerUsername(trainer.getUser().getUsername());

                Optional<TrainingType> byUsername = trainingTypeRepository.findByUsername(trainer.getUser().getUsername());

                byUsername.ifPresent(trainingType -> resp.setType(trainingType.getName()));
            }
            finalList.add(resp);

        }

        return finalList;
    }

}
