package uz.utkirbek.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SELECT_ALL = "select u.* from users u";
    private static final String SELECT_BY_USERNAME = "SELECT u FROM User u WHERE u.username = :username";
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<User> create(User item) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            if (item.getId() == 0) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            return Optional.ofNullable(item);
        } finally {
            transaction.commit();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        User user = entityManager.find(User.class, id);
        return user == null ? Optional.empty() : Optional.ofNullable(user);
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
    public Optional<User> findByUsername(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery(SELECT_BY_USERNAME, User.class);
            query.setParameter("username", username);

            User user = query.getSingleResult();

            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
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
    public Optional<Boolean> changeStatus(String username, Boolean isActive) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Optional<User> userOptional = findByUsername(username);
            if (!userOptional.isPresent()) {
                return Optional.empty();
            }
            User user = userOptional.get();
            user.setActive(isActive);
            entityManager.flush();

            return Optional.of(true);
        } catch (Exception e) {
            return Optional.of(false);
        } finally {
            transaction.commit();
        }

    }
}
