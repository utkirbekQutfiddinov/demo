package uz.utkirbek.service;

public interface BaseDeleteService<T> extends BaseUpdateService<T> {
    void delete(Integer id);
}
