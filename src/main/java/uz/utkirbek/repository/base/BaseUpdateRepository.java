package uz.utkirbek.repository.base;

import java.util.Optional;

public interface BaseUpdateRepository<T> extends BaseRepository<T> {
    Optional<T> update(T item);
}
