package uz.utkirbek.service.base;

public interface BaseDeleteService<T, K> extends BaseUpdateService<T, K> {
    Boolean delete(K key);
}
