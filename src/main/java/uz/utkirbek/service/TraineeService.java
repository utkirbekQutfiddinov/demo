package uz.utkirbek.service;

import uz.utkirbek.model.Trainee;

public interface TraineeService extends BaseService<Trainee>{
    Trainee getByUsername(String username);
    Boolean changePassword(Integer traineeId, String password);
    Boolean changeStatus(Integer trainerId);
}
