package uz.utkirbek.service;

import uz.utkirbek.model.User;

public interface UserService extends BaseService<User>{
    Boolean changePassword(Integer id, String password);
}
