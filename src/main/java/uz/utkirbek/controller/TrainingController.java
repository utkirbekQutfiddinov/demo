package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

        try {
            if (!isValidTrainingDto(dto)) {
                LOGGER.info("Empty parameters: " + dto);
                return new ResponseEntity<>("Empty parameters: " + dto, HttpStatus.BAD_REQUEST);
            }


            Training training = trainingService.add(dto);

            if (training == null) {
                LOGGER.info("Error on creating: " + dto);
                return ResponseEntity.internalServerError().build();
            }

            return new ResponseEntity<>("Success", HttpStatus.CREATED);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

    private boolean isValidTrainingDto(TrainingDto dto) {
        return dto.getDate() != null && dto.getDuration() != null
                && dto.getName() != null && dto.getTraineeUsername() != null
                && dto.getTrainerUsername() != null;
    }
}
