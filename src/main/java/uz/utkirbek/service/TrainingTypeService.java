package uz.utkirbek.service;

import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.service.base.BaseService;

public interface TrainingTypeService extends BaseService<TrainingType, TrainingType> {
    TrainingType getByName(String name);
}
