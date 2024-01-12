package uz.utkirbek.service;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll();

    T getOne(Integer id);

    void add(T bean);

    default void update(T bean) {
        System.out.println("Message from Default update method");
    }

    default void delete(Integer id) {
        System.out.println("Message from Default delete method");
    }
}
