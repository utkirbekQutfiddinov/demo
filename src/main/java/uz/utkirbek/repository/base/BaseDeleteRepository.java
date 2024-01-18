package uz.utkirbek.repository.base;

public interface BaseDeleteRepository<T, K> extends BaseUpdateRepository<T, K> {
    void delete(T item);
}
