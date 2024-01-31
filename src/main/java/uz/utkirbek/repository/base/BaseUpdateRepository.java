package uz.utkirbek.repository.base;

import java.util.Optional;

public interface BaseUpdateRepository<T, DTO> extends BaseRepository<T, DTO> {
    Optional<T> update(T item);
}
