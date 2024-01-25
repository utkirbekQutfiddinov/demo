package uz.utkirbek.repository.base;

public interface BaseDeleteRepository<T> extends BaseUpdateRepository<T> {
    void delete(T item);
}
