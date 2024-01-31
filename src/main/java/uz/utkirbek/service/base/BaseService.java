package uz.utkirbek.service.base;

import java.util.List;

public interface BaseService<T, DTO> {
    List<T> getAll();

    T getOne(int id);

    T add(DTO bean);
}
