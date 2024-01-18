package uz.utkirbek.repository;

import uz.utkirbek.repository.base.BaseDeleteRepository;
import uz.utkirbek.model.Trainee;

import java.util.Optional;

public interface TraineeRepository extends BaseDeleteRepository<Trainee, Integer> {
    Optional<Trainee> findByUsername(String username);
}
