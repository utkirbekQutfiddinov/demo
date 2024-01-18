package uz.utkirbek.repository.base;

import java.util.Optional;

public interface BaseUpdateRepository<T,K> extends BaseRepository<T,K> {
    Optional<T> update(T item);
}
