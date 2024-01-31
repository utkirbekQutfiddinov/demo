package uz.utkirbek.repository.base;

public interface BaseDeleteRepository<T, DTO> extends BaseUpdateRepository<T, DTO> {
    void delete(T item);
}
