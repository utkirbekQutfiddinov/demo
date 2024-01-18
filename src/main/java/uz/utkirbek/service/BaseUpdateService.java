package uz.utkirbek.service;

public interface BaseUpdateService<T> extends BaseService<T> {
    void update(T bean);
}
