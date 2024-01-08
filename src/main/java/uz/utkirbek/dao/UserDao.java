package uz.utkirbek.dao;

import uz.utkirbek.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getOne(Integer id);

    void add(User bean) throws Exception;

    void update(User bean) throws Exception;

    void delete(Integer id) throws Exception;
}
