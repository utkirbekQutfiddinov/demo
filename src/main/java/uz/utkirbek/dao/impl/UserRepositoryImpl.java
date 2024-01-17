package uz.utkirbek.dao.impl;

import org.springframework.stereotype.Repository;
import uz.utkirbek.dao.UserRepository;
import uz.utkirbek.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Override
    public Optional<User> create(User item) {
        return Optional.empty();
    }

    @Override
    public Optional<User> readOne(Integer key) {
        return Optional.empty();
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public Optional<User> update(User item) {
        return Optional.empty();
    }

    @Override
    public void delete(Integer key) {

    }
}
