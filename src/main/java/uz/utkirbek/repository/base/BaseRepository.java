package uz.utkirbek.repository.base;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> create(T item);

    Optional<T> readOne(int id);

    List<T> readAll();
}
