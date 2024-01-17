package uz.utkirbek.dao;

import uz.utkirbek.model.Trainee;

import java.util.Optional;

public interface TraineeRepository extends BaseRepository<Trainee, Integer> {
    Optional<Trainee> findByUsername(String username);
}
