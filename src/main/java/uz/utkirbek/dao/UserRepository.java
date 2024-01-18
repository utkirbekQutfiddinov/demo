package uz.utkirbek.dao;

import uz.utkirbek.dao.base.BaseDeleteRepository;
import uz.utkirbek.model.User;

import java.util.Optional;

public interface UserRepository extends BaseDeleteRepository<User, Integer> {
    Optional<User> findByUserName(String username);
    Optional<Boolean> changePassword(Integer id, String password);
    Optional<Boolean> changeStatus(Integer id);
}
