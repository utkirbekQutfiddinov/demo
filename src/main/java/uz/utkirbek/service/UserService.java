package uz.utkirbek.service;

import uz.utkirbek.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getOne(Integer id);

    void add(User bean);

    void update(User bean);

    void delete(Integer id);
}
