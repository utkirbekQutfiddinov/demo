package uz.utkirbek;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class Main {


    @PersistenceContext
    EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("epamTask");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        entityManager.close();
        entityManagerFactory.close();
    }
}
