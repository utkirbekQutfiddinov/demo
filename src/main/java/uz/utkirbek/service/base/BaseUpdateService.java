package uz.utkirbek.service.base;

public interface BaseUpdateService<T> extends BaseService<T> {
    T update(T item);
}
