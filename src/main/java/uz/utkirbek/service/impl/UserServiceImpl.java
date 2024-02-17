package uz.utkirbek.service.impl;

import org.springframework.stereotype.Service;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.UserService;

import java.util.Collections;
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
        try {
            List<User> all = repository.findAll();
            return all;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public User getOne(int id) {
        try {
            Optional<User> optional = repository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User add(User bean) {
        try {
            bean.setUsername(generateUsername(bean.getFirstname(), bean.getLastname()));
            bean.setPassword(generatePassword());
            return repository.create(bean).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User update(User bean) {
        try {
            Optional<User> update = repository.update(bean);
            return update.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {
        try {
            User user = getOne(id);
            if (user == null) {
                return false;
            }
            repository.delete(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean changePassword(int id, String password) {
        try {
            Optional<Boolean> aBoolean = repository.changePassword(id, password);
            return aBoolean.orElse(false);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            Optional<User> byUserName = repository.findByUsername(username);

            if (!byUserName.isPresent()) {
                return null;
            }
            User user = byUserName.get();

            return user.getPassword().equals(password) ? user : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String generateUsername(String firstname, String lastname) {
        String baseUsername = firstname + lastname;
        String username = baseUsername;

        int serialNumber = 1;
        while (usernameExists(username)) {
            username = baseUsername + serialNumber;
            serialNumber++;
        }

        return username;
    }

    private boolean usernameExists(String username) {
        Optional<User> existingUser = repository.findByUsername(username);
        return existingUser.isPresent();
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }


}
