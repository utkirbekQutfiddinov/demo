package uz.utkirbek.service;

import uz.utkirbek.model.Trainee;
import uz.utkirbek.service.base.BaseDeleteService;

public interface TraineeService extends BaseDeleteService<Trainee, Integer> {
    Trainee getByUsername(String username);
    Boolean changePassword(Integer traineeId, String password);
    Boolean changeStatus(Integer trainerId);
}
