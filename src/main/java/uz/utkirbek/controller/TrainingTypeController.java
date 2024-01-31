package uz.utkirbek.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.service.TrainingTypeService;

import java.util.List;

@RestController
@RequestMapping("/training-types")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingType>> getAll() {
        return ResponseEntity.ok(trainingTypeService.getAll());
    }

}
