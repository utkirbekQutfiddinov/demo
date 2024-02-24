package uz.utkirbek.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class HibernateConfig {

    @Bean
    @Profile("prod")
    public EntityManager getEntityManager() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("epamTask");
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    @Profile("dev")
    public EntityManager getEntityManagerDev() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("epamTaskDev");
        return entityManagerFactory.createEntityManager();
    }
}
