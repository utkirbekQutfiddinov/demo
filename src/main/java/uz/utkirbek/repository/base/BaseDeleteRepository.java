package uz.utkirbek.repository.base;

public interface BaseDeleteRepository<T, DTO> extends BaseUpdateRepository<T, DTO> {
    boolean delete(T item) throws Exception;
}
