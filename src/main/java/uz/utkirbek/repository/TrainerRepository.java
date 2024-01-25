package uz.utkirbek.repository;

import uz.utkirbek.repository.base.BaseUpdateRepository;
import uz.utkirbek.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends BaseUpdateRepository<Trainer> {
    Optional<Trainer> findByUsername(String username);
    List<Trainer> getNotAssignedAndActive();
}
