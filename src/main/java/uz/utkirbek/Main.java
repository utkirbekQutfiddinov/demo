package uz.utkirbek;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import uz.utkirbek.dao.impl.UserRepositoryImpl;
import uz.utkirbek.model.User;

import java.util.List;

public class Main {
    @Autowired
    static EntityManager entityManager;

    public static void main(String[] args) {



        UserRepositoryImpl repo=new UserRepositoryImpl(entityManager);

        List<User> users=repo.readAll();
        System.out.println(users.size());

    }
}
