package uz.utkirbek.service.base;

public interface BaseUpdateService<T, DTO> extends BaseService<T, DTO> {
    T update(T item);
}
