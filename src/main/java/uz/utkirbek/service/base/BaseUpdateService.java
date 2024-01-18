package uz.utkirbek.service.base;

public interface BaseUpdateService<T, K> extends BaseService<T, K> {
    T update(T item);
}
