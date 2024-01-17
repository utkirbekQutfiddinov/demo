package uz.utkirbek.dao;

import uz.utkirbek.model.Trainer;

import java.util.Optional;

public interface TrainerRepository extends BaseRepository<Trainer, Integer> {
    Optional<Trainer> findByUsername(String username);
}
