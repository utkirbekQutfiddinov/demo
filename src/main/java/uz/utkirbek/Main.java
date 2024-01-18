package uz.utkirbek;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.dao.impl.UserRepositoryImpl;
import uz.utkirbek.model.User;

import java.util.List;
import java.util.Objects;

public class Main {
//    @Autowired
//    EntityManager entityManager;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");

        EntityManager entityManager=context.getBean(EntityManager.class);
        UserRepositoryImpl repo=new UserRepositoryImpl(entityManager);

        List<User> users=repo.readAll();
        for (User user: users) {
            System.out.println(Objects.toString(user));
        }

    }
}
