package uz.utkirbek.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private static final String SELECT_ALL = "select u.* from users u";
    private static final String SELECT_BY_USERNAME = "select u.* from users u where u.username=:username";
    private static final String USERNAME = "username";
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<User> create(User item) {
        if (item.getFirstname() == null || item.getLastname() == null || item.getUsername() == null
                || item.getPassword() == null) {
            LOGGER.trace("Empty parameters");
            return Optional.empty();
        }

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
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
    public Optional<User> findById(int id) {
        User user = entityManager.find(User.class, id);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        Query nativeQuery = entityManager.createNativeQuery(SELECT_ALL);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<User> update(User item) {
        return create(item);
    }

    @Override
    public void delete(User item) {
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
    public Optional<User> findByUserName(String username) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Query nativeQuery = entityManager.createNativeQuery(SELECT_BY_USERNAME);
            nativeQuery.setParameter(USERNAME, username);
            User user = (User) nativeQuery.getSingleResult();
            transaction.commit();

            return user != null ? Optional.of(user) : Optional.empty();
        } catch (Exception e) {
            transaction.rollback();
            LOGGER.debug(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Boolean> changePassword(int id, String password) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            User user = entityManager.find(User.class, id);
            user.setPassword(password);
            entityManager.flush();

            return Optional.of(true);
        } finally {
            transaction.commit();
        }

    }

    @Override
    public Optional<Boolean> changeStatus(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            User user = entityManager.find(User.class, id);
            boolean isActive = user.getIsActive();
            user.setIsActive(!isActive);
            entityManager.flush();

            return Optional.of(isActive);
        } finally {
            transaction.commit();
        }

    }
}
