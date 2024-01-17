package uz.utkirbek.dao;

import java.util.List;
import java.util.Optional;

public interface BaseRepository <T,K>{
    Optional<T> create(T item);
    Optional<T> readOne(K key);
    List<T> readAll();
    Optional<T> update(T item);
    void delete(K key);
}
