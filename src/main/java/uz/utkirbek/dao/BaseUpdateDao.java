package uz.utkirbek.dao;

public interface BaseUpdateDao<T> extends BaseDao<T> {
    void update(T bean);
}
