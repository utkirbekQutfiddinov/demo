package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.dao.UserRepository;
import uz.utkirbek.model.User;
import uz.utkirbek.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
        return repository.readAll();
    }

    @Override
    public User getOne(Integer id) {
        Optional<User> optional=repository.readOne(id);
        return optional.orElse(null);
    }

    @Override
    public void add(User bean) {
        repository.create(bean);
    }

    @Override
    public void update(User bean) {
        repository.update(bean);
    }

    @Override
    public void delete(Integer id) {
        User user=getOne(id);
        if (user!=null)
            repository.delete(user);
    }
}
