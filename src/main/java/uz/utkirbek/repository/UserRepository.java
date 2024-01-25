package uz.utkirbek.repository;

import uz.utkirbek.repository.base.BaseDeleteRepository;
import uz.utkirbek.model.User;

import java.util.Optional;

public interface UserRepository extends BaseDeleteRepository<User> {
    Optional<User> findByUserName(String username);
    Optional<Boolean> changePassword(int id, String password);
    Optional<Boolean> changeStatus(int id);
}
