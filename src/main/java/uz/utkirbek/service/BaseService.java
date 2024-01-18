package uz.utkirbek.service;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll();

    T getOne(Integer id);

    void add(T bean);

}
