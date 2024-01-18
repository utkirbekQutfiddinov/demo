package uz.utkirbek.dao.base;

public interface BaseDeleteRepository<T, K> extends BaseUpdateRepository<T, K> {
    void delete(T item);
}
