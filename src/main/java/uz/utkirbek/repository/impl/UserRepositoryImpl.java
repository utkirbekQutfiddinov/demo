package uz.utkirbek.repository.impl;

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
    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<User> create(User item) {
        if (item.getFirstname() == null || item.getLastname() == null || item.getUsername() == null
                || item.getPassword() == null) {
            return Optional.empty();
        }

        EntityTransaction transaction=entityManager.getTransaction();

        try {
            transaction.begin();
            if (item.getId() == 0) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            return Optional.of(item);
        }finally {
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
        String sql = "select u.* from users u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<User> update(User item) {
        return create(item);
    }

    @Override
    public void delete(User item) {
        EntityTransaction transaction=entityManager.getTransaction();
        transaction.begin();

        if (entityManager.contains(item)) {
            entityManager.remove(item);
        }
        transaction.commit();

    }

    @Override
    public Optional<User> findByUserName(String username) {
        EntityTransaction transaction=entityManager.getTransaction();
        try {
            transaction.begin();

            String sql = "select u.* from users u where u.username=:username";
            Query nativeQuery = entityManager.createNativeQuery(sql);
            nativeQuery.setParameter("username", username);
            User user = (User) nativeQuery.getSingleResult();
            transaction.commit();

            return user != null ? Optional.of(user) : Optional.empty();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Boolean> changePassword(int id, String password) {
        EntityTransaction transaction=entityManager.getTransaction();
        try {
            transaction.begin();

            User user = entityManager.find(User.class, id);
            user.setPassword(password);
            entityManager.flush();

            return Optional.of(true);
        }finally {
            transaction.commit();
        }

    }

    @Override
    public Optional<Boolean> changeStatus(int id) {
        EntityTransaction transaction=entityManager.getTransaction();
        try {
            transaction.begin();

            User user = entityManager.find(User.class, id);
            boolean currentStatus = user.getIsActive();
            user.setIsActive(!currentStatus);
            entityManager.flush();

            return Optional.of(currentStatus);
        } finally {
            transaction.commit();
        }

    }
}
