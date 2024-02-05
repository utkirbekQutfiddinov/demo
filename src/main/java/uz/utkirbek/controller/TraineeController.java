package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
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

        if (traineeDto == null || traineeDto.getFirstName() == null || traineeDto.getLastName() == null) {
            LOGGER.error("Empty parameters: " + traineeDto);
            return ResponseEntity.badRequest().body(null);
        }

        Trainee addedTrainee = traineeService.add(traineeDto);
        if (addedTrainee == null) {
            LOGGER.error("Error during creation: " + traineeDto);
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(new RegisterResponse(addedTrainee.getUser().getUsername(),
                addedTrainee.getUser().getPassword()));
    }

    @GetMapping
    public ResponseEntity<TraineeResponse> getByUsername(@RequestParam String username) {

        if (username == null) {
            LOGGER.error("Empty parameters: username is null");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Trainee trainee = traineeService.getByUsername(username);
        if (trainee == null) {
            LOGGER.error("Trainee does not exist by username: " + username);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }

        try {
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

            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<TraineeResponse> updateProfile(@RequestBody TraineeUpdateDto dto) {

        if (!isValidDtoForUpdating(dto)) {
            LOGGER.error("Empty parameters: " + dto);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Trainee trainee = traineeService.getByUsername(dto.getUsername());
        if (trainee == null) {
            LOGGER.error("Trainee not found: username=" + dto.getUsername());
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
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
    }


    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String username) {

        if (username == null) {
            LOGGER.error("Empty parameters: username is null");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Trainee trainee = traineeService.getByUsername(username);

        if (trainee == null) {
            LOGGER.error("Trainee not found: username = " + username);
            return new ResponseEntity<>("Error", HttpStatusCode.valueOf(404));
        }

        Boolean isDeleted = traineeService.delete(trainee.getId());

        if (isDeleted) {
            return new ResponseEntity<>("Success", HttpStatusCode.valueOf(200));
        } else {
            LOGGER.error("Trainee deleting failed: username = " + username);
            return new ResponseEntity<>("Error", HttpStatusCode.valueOf(501));
        }
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TraineeTrainingResponse>> getTrainings(@RequestParam String username,
                                                                      @RequestParam(required = false) LocalDate periodFrom,
                                                                      @RequestParam(required = false) LocalDate periodTo,
                                                                      @RequestParam(required = false) String trainerUsername,
                                                                      @RequestParam(required = false) String trainingType) {

        if (username == null) {
            LOGGER.error("Empty parameters: username is null");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        TrainingFiltersDto filter = new TrainingFiltersDto(username, periodFrom, periodTo, trainerUsername, trainingType);

        try {
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
                LOGGER.error("Empty parameters: username is null");
                return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
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

        if (username == null || isActive == null) {
            LOGGER.error("Empty parameters");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Boolean isChanged = traineeService.changeStatus(username, isActive);
        if (!isChanged) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");

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
