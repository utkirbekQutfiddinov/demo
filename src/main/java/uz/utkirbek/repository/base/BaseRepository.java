package uz.utkirbek.repository.base;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, DTO> {
    Optional<T> create(DTO item);

    Optional<T> findById(int id);

    List<T> findAll();
}
