package uz.utkirbek.service.base;

import java.util.List;

public interface BaseService<T, K> {
    List<T> getAll();

    T getOne(K key);

    void add(T bean);
}
