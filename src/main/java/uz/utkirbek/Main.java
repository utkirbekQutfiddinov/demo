package uz.utkirbek;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.dao.impl.UserRepositoryImpl;
import uz.utkirbek.model.User;

import java.util.List;

public class Main {
//    @Autowired
//    EntityManager entityManager;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");

        EntityManager entityManager=context.getBean(EntityManager.class);
        UserRepositoryImpl repo=new UserRepositoryImpl(entityManager);

        List<User> users=repo.readAll();
        System.out.println(users.size());

    }
}
