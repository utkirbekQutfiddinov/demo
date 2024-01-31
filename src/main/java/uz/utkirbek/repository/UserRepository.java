package uz.utkirbek.repository;

import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.base.BaseDeleteRepository;

import java.util.Optional;

public interface UserRepository extends BaseDeleteRepository<User, User> {
    Optional<User> findByUsername(String username);

    Optional<Boolean> changePassword(int id, String password);

    Optional<Boolean> changeStatus(String username, Boolean isActive);
}
