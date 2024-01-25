package uz.utkirbek.service;

import uz.utkirbek.model.User;
import uz.utkirbek.service.base.BaseDeleteService;

public interface UserService extends BaseDeleteService<User> {
    Boolean changePassword(int id, String password);
}
