package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeController.class);

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingType>> getAll() {

        try {
            List<TrainingType> resultList = trainingTypeService.getAll();

            return ResponseEntity.ok(resultList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

}
