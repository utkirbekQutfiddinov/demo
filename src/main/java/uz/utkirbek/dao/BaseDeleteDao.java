package uz.utkirbek.dao;

public interface BaseDeleteDao<T> extends BaseUpdateDao<T> {
    void delete(Integer id);
}
