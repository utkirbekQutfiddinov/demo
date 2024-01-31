package uz.utkirbek.service.base;

public interface BaseDeleteService<T, DTO> extends BaseUpdateService<T, DTO> {
    Boolean delete(int id);
}
