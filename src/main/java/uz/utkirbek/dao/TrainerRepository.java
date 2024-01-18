package uz.utkirbek.dao;

import uz.utkirbek.dao.base.BaseUpdateRepository;
import uz.utkirbek.model.Trainer;

import java.util.Optional;

public interface TrainerRepository extends BaseUpdateRepository<Trainer, Integer> {
    Optional<Trainer> findByUsername(String username);
}
