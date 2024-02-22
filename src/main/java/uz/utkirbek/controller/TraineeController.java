package uz.utkirbek.controller;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.utkirbek.model.dto.TraineeDto;
import uz.utkirbek.model.dto.TraineeUpdateDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.Training;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.model.response.*;
import uz.utkirbek.service.TraineeService;
import uz.utkirbek.service.TrainingService;
import uz.utkirbek.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/trainees")
public class TraineeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeController.class);
    private static final Counter counter =
            Counter.build()
                    .name("traineeApiCounter")
                    .help("Total number of calls to the TraineeController's APIs")
                    .register();

    private final TraineeService traineeService;
    private final TrainingService trainingService;
    private final UserService userService;


    public TraineeController(TraineeService traineeService, TrainingService trainingService, UserService userService) {
        this.traineeService = traineeService;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody TraineeDto traineeDto) {

        try {
            if (traineeDto == null || traineeDto.getFirstName() == null || traineeDto.getLastName() == null) {
                LOGGER.info("Empty parameters: " + traineeDto);
                return ResponseEntity.badRequest().body(null);
            }

            Trainee addedTrainee = traineeService.add(traineeDto);
            if (addedTrainee == null) {
                LOGGER.info("Error during creation: " + traineeDto);
                return ResponseEntity.internalServerError().body(null);
            }

            return ResponseEntity.ok(new RegisterResponse(addedTrainee.getUser().getUsername(),
                    addedTrainee.getUser().getRawPassword()));
        } catch (Exception e) {
            LOGGER.error("Error during creation: " + traineeDto);
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<TraineeResponse> getByUsername(@RequestParam String username) {

        try {
            if (username == null) {
                LOGGER.info("Empty parameters: username is null");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Trainee trainee = traineeService.getByUsername(username);
            if (trainee == null) {
                LOGGER.info("Trainee does not exist by username: " + username);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            TraineeResponse response = new TraineeResponse();

            User traineeUser = trainee.getUser();
            response.setActive(traineeUser.isActive());
            response.setFirstName(traineeUser.getFirstname());
            response.setLastName(traineeUser.getLastname());
            response.setBirthDate(trainee.getBirthdate());
            response.setAddress(trainee.getAddress());
            response.setUsername(username);

            List<TraineeTrainerResponse> trainerResponses = getTrainerResponses(trainee.getTrainings());
            response.setTrainersList(trainerResponses);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<TraineeResponse> updateProfile(@RequestBody TraineeUpdateDto dto) {

        try {
            if (!isValidDtoForUpdating(dto)) {
                LOGGER.info("Empty parameters: " + dto);
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Trainee trainee = traineeService.getByUsername(dto.getUsername());
            if (trainee == null) {
                LOGGER.info("Trainee not found: username=" + dto.getUsername());
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            trainee.setAddress(dto.getAddress());
            trainee.setBirthdate(dto.getBirthDate());

            traineeService.update(trainee);

            User user = trainee.getUser();
            user.setFirstname(dto.getFirstName());
            user.setLastname(dto.getLastName());
            user.setActive(dto.isActive());

            userService.update(user);

            return getByUsername(dto.getUsername());
        } catch (Exception e) {
            LOGGER.error("Error on updating: " + dto);
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String username) {

        try {
            if (username == null) {
                LOGGER.info("Empty parameters: username is null");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Trainee trainee = traineeService.getByUsername(username);

            if (trainee == null) {
                LOGGER.info("Trainee not found: username = " + username);
                return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
            }

            Boolean isDeleted = traineeService.delete(trainee.getId());

            if (isDeleted) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            } else {
                LOGGER.error("Trainee deleting failed: username = " + username);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            LOGGER.error("error on deleting: " + username);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TraineeTrainingResponse>> getTrainings(@RequestParam String username,
                                                                      @RequestParam(required = false) LocalDate periodFrom,
                                                                      @RequestParam(required = false) LocalDate periodTo,
                                                                      @RequestParam(required = false) String trainerUsername,
                                                                      @RequestParam(required = false) String trainingType) {

        try {
            if (username == null) {
                LOGGER.info("Empty parameters: username is null");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            TrainingFiltersDto filter = new TrainingFiltersDto(username, periodFrom, periodTo, trainerUsername, trainingType);

            List<TrainingResponse> trainings = trainingService.getByCriteria(filter);

            List<TraineeTrainingResponse> traineeTrainingsResponse = trainings.stream().map((training) -> {
                TraineeTrainingResponse response = new TraineeTrainingResponse();
                response.setDate(training.getDate());
                response.setName(training.getName());
                response.setDuration(training.getDuration());
                response.setTrainerUsername(training.getTrainerUsername());
                response.setType(training.getType());
                return response;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(traineeTrainingsResponse);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/notAssignedActiveTrainees")
    public ResponseEntity<List<TraineeTrainerResponse>> getActiveNotAssignedTrainers(@RequestParam String username) {

        try {
            if (username == null) {
                LOGGER.info("Empty parameters: username is null");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            List<TraineeTrainerResponse> notAssignedActiveTrainers = traineeService.getNotAssignedAcitiveTrainers(username);

            return ResponseEntity.ok(notAssignedActiveTrainers);
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
            return ResponseEntity.internalServerError().build();
        }

    }

    @PatchMapping
    public ResponseEntity<String> changeStatus(@RequestParam String username, @RequestParam Boolean isActive) {

        try {
            if (username == null || isActive == null) {
                LOGGER.info("Empty parameters");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            Boolean isChanged = traineeService.changeStatus(username, isActive);
            if (!isChanged) {
                return ResponseEntity.internalServerError().build();
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            LOGGER.error("error on changing status: " + username + ", status: " + isActive);
            return ResponseEntity.internalServerError().build();
        }

    }


    private boolean isValidDtoForUpdating(TraineeUpdateDto dto) {
        return dto != null && dto.getUsername() != null && dto.getFirstName() != null && dto.getLastName() != null;
    }

    private List<TraineeTrainerResponse> getTrainerResponses(List<Training> trainings) {

        return trainings.stream().map((training) -> {

            Trainer trainer = training.getTrainer();
            User trainerUser = trainer.getUser();

            return new TraineeTrainerResponse(trainerUser.getUsername(), trainerUser.getFirstname(), trainerUser.getLastname(), trainer.getTrainingType());
        }).collect(Collectors.toList());
    }
}
