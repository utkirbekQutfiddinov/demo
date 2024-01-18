package uz.utkirbek.service;

import uz.utkirbek.model.User;
import uz.utkirbek.service.base.BaseDeleteService;

public interface UserService extends BaseDeleteService<User, Integer> {
    Boolean changePassword(Integer id, String password);
}
