package uz.utkirbek.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.UserRepository;
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
        try {
            entityManager.getTransaction().begin();
            if (item.getId() == null) {
                entityManager.persist(item);
            } else {
                item = entityManager.merge(item);
            }
            entityManager.getTransaction().commit();
            return Optional.of(item);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> readOne(Integer key) {
        User user = entityManager.find(User.class, key);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public List<User> readAll() {
        String sql="select u.* from users u";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        return nativeQuery.getResultList();
    }

    @Override
    public Optional<User> update(User item) {
        return create(item);
    }

    @Override
    public void delete(User item) {
        entityManager.getTransaction().begin();

        if (entityManager.contains(item)) {
            entityManager.remove(item);
        }
        entityManager.getTransaction().commit();

    }
}