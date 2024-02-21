package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.dto.TrainerUpdateDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.dto.TrainingUpdateDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.model.response.*;
import uz.utkirbek.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerController.class);

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    public TrainerController(TrainerService trainerService, TraineeService traineeService, TrainingService trainingService, UserService userService, TrainingTypeService trainingTypeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
        this.trainingService = trainingService;
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
    }

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody TrainerDto trainerDto) {

        if (trainerDto.getFirstName() == null || trainerDto.getLastName() == null
                || trainerDto.getSpecialization() == null) {
            LOGGER.error("Empty parameters: " + trainerDto);
            return ResponseEntity.badRequest().body(null);
        }

        try {
            Trainer addedTrainer = trainerService.add(trainerDto);
            if (addedTrainer == null) {
                LOGGER.error("Error during creation: " + trainerDto);
                return ResponseEntity.internalServerError().body(null);
            }

            return ResponseEntity.ok(new RegisterResponse(addedTrainer.getUser().getUsername(),
                    addedTrainer.getUser().getRawPassword()));
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<TrainerResponse> getByUsername(@RequestParam String username) {

        try {
            Trainer trainer = trainerService.getByUsername(username);
            if (trainer == null) {
                LOGGER.error("Trainer does not exist by username: " + username);
                return ResponseEntity.notFound().build();
            }

            TrainerResponse response = new TrainerResponse();

            User trainerUser = trainer.getUser();
            response.setActive(trainerUser.isActive());
            response.setFirstName(trainerUser.getFirstname());
            response.setLastName(trainerUser.getLastname());
            response.setSpecialization(trainer.getTrainingType());

            TrainingFiltersDto filter = new TrainingFiltersDto();
            filter.setTraineeUsername(username);

            List<TrainerTraineeResponse> trainerResponses = getTraineesList(trainingService.getByCriteria(filter));
            response.setTraineesList(trainerResponses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

    }

    @PutMapping
    public ResponseEntity<TrainerResponse> updateProfile(@RequestBody TrainerUpdateDto dto) {

        try {
            if (!isValidTrainerDtoForUpdating(dto)) {
                LOGGER.error("Empty parameters: " + dto);
                return ResponseEntity.badRequest().build();
            }

            Trainer trainer = trainerService.getByUsername(dto.getUsername());
            if (trainer == null) {
                LOGGER.error("Trainer not found: username=" + dto.getUsername());
                return ResponseEntity.notFound().build();
            }

            TrainingType trainingType = trainingTypeService.getByName(dto.getSpecialization().getName());
            trainer.setTrainingType(trainingType);

            trainerService.update(trainer);

            User user = trainer.getUser();
            user.setFirstname(dto.getFirstName());
            user.setLastname(dto.getLastName());
            user.setActive(dto.isActive());

            userService.update(user);

            return getByUsername(dto.getUsername());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainerTrainingResponse>> getTrainings(@RequestParam String username,
                                                                      @RequestParam(required = false) LocalDate periodFrom,
                                                                      @RequestParam(required = false) LocalDate periodTo,
                                                                      @RequestParam(required = false) String traineeUserName,
                                                                      @RequestParam(required = false) String trainingType) {


        try {
            TrainingFiltersDto filter = new TrainingFiltersDto(traineeUserName, periodFrom, periodTo, username, trainingType);

            List<TrainingResponse> trainings = trainingService.getByCriteria(filter);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            List<TrainerTrainingResponse> traineeTrainingsResponse = trainings.stream().map((training) -> {
                TrainerTrainingResponse response = new TrainerTrainingResponse();
                try {
                    response.setDate(sdf.parse(training.getDate().toString()));
                } catch (ParseException e) {
                    LOGGER.error(e.getMessage());
                }
                response.setType(training.getType());
                response.setName(training.getName());
                response.setDuration(training.getDuration());
                response.setTraineeUsername(training.getTraineeUsername());
                return response;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(traineeTrainingsResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/training-trainer")
    public ResponseEntity<String> updateTrainerList(@RequestBody TrainingUpdateDto dto) {

        try {
            if (dto.getTrainingId() == null || dto.getTrainerUsername() == null) {
                LOGGER.error("Empty parameters");
                return new ResponseEntity<>("Empty parameters", HttpStatus.BAD_REQUEST);
            }

            Boolean isChanged = trainingService.updateTrainer(dto.getTrainingId(), dto.getTrainerUsername());

            if (!isChanged) {
                LOGGER.trace("Error on changing trainer of training: " + dto);
                return ResponseEntity.internalServerError().body("Error on changing trainer of training");
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            LOGGER.error("error on updating: " + dto);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping
    public ResponseEntity<String> changeStatus(@RequestParam String username, @RequestParam Boolean isActive) {

        try {
            if (username == null || isActive == null) {
                LOGGER.error("Empty parameters");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Boolean isChanged = trainerService.changeStatus(username, isActive);
            if (!isChanged) {
                LOGGER.error("error on chaning status: " + username + ", status: " + isActive);
                return ResponseEntity.internalServerError().build();
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            LOGGER.error("error on chaning status: " + username + ", status: " + isActive);
            return ResponseEntity.internalServerError().build();
        }

    }

    private boolean isValidTrainerDtoForUpdating(TrainerUpdateDto dto) {
        return dto != null && dto.getUsername() != null && dto.getFirstName() != null
                && dto.getLastName() != null && dto.getSpecialization() != null;
    }

    private List<TrainerTraineeResponse> getTraineesList(List<TrainingResponse> trainings) {

        return trainings.stream().map((training) -> {

            Trainee trainee = traineeService.getByUsername(training.getTraineeUsername());
            User traineeUser = trainee.getUser();

            return new TrainerTraineeResponse(traineeUser.getUsername(), traineeUser.getFirstname(), traineeUser.getLastname());
        }).collect(Collectors.toList());
    }
}
