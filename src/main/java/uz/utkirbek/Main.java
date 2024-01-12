package uz.utkirbek;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uz.utkirbek.service.ServiceFacade;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("uz.utkirbek");
        ServiceFacade service= context.getBean(ServiceFacade.class);

        service.getAllUsers().forEach(System.out::println);

    }
}
