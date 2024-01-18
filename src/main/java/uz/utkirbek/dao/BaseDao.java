package uz.utkirbek.dao;

import java.util.List;

public interface BaseDao<T> {
    List<T> getAll();

    T getOne(Integer id);

    void add(T bean);
}
