package uz.utkirbek.repository.base;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, K> {
    Optional<T> create(T item);

    Optional<T> readOne(K key);

    List<T> readAll();
}
