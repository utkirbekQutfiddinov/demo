package uz.utkirbek.service.base;

public interface BaseDeleteService<T> extends BaseUpdateService<T> {
    Boolean delete(int id);
}
