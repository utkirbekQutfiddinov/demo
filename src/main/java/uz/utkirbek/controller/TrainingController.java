package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.utkirbek.model.dto.TrainingDto;
import uz.utkirbek.model.dto.TrainingUpdateDto;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.service.TrainingService;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingController.class);

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }


    @PostMapping
    public ResponseEntity<String> add(@RequestBody TrainingDto dto) {

        if (!isValidTrainingDto(dto)) {
            LOGGER.error("Empty parameters: " + dto);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        try {

            Training training = trainingService.add(dto);

            if (training == null) {
                LOGGER.error("Error on creating: " + dto);
                return ResponseEntity.internalServerError().build();
            }

            return new ResponseEntity<>("Success", HttpStatus.CREATED);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping
    public ResponseEntity<String> updateTrainerList(@RequestBody TrainingUpdateDto dto) {

        if (dto.getTrainingId() == null || dto.getTrainerUsername() == null) {
            LOGGER.error("Empty parameters");
            return new ResponseEntity<>("Empty parameters", HttpStatusCode.valueOf(400));
        }

        Boolean isChanged = trainingService.updateTrainer(dto.getTrainingId(), dto.getTrainerUsername());

        if (!isChanged) {
            LOGGER.trace("Error on changing trainer of training: " + dto);
            return ResponseEntity.internalServerError().body("Error on changing trainer of training");
        }

        return ResponseEntity.ok("Success");
    }

    private boolean isValidTrainingDto(TrainingDto dto) {
        return dto.getDate() != null && dto.getDuration() != null
                && dto.getName() != null && dto.getTraineeUsername() != null
                && dto.getTrainerUsername() != null;
    }
}
