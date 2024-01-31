package uz.utkirbek.service;

import uz.utkirbek.model.entity.User;
import uz.utkirbek.service.base.BaseDeleteService;

public interface UserService extends BaseDeleteService<User, User> {
    Boolean changePassword(int id, String password);

    User findByUsernameAndPassword(String username, String password);
}
