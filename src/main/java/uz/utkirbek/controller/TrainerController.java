package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.utkirbek.model.dto.TrainerDto;
import uz.utkirbek.model.dto.TrainerUpdateDto;
import uz.utkirbek.model.dto.TrainingFiltersDto;
import uz.utkirbek.model.entity.Trainee;
import uz.utkirbek.model.entity.Trainer;
import uz.utkirbek.model.entity.TrainingType;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.model.response.*;
import uz.utkirbek.service.TraineeService;
import uz.utkirbek.service.TrainerService;
import uz.utkirbek.service.TrainingTypeService;
import uz.utkirbek.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainers")
public class TrainerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerController.class);

    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    public TrainerController(TrainerService trainerService, TraineeService traineeService, UserService userService, TrainingTypeService trainingTypeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
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
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(new RegisterResponse(addedTrainer.getUser().getUsername(),
                    addedTrainer.getUser().getPassword()));
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<TrainerResponse> getByUsername(@RequestParam String username) {

        if (username == null) {
            LOGGER.error("Empty parameters: username is null");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Trainer trainer = trainerService.getByUsername(username);
        if (trainer == null) {
            LOGGER.error("Trainer does not exist by username: " + username);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }

        TrainerResponse response = new TrainerResponse();

        try {
            User trainerUser = trainer.getUser();
            response.setActive(trainerUser.isActive());
            response.setFirstName(trainerUser.getFirstname());
            response.setLastName(trainerUser.getLastname());
            response.setSpecialization(trainer.getTrainingType());

            TrainingFiltersDto filter = new TrainingFiltersDto();
            filter.setTraineeUsername(username);

            List<TrainerTraineeResponse> trainerResponses = getTraineesList(trainerService.getByCriteria(filter));
            response.setTraineesList(trainerResponses);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @PutMapping
    public ResponseEntity<TrainerResponse> updateProfile(@RequestBody TrainerUpdateDto dto) {

        if (!isValidTrainerDtoForUpdating(dto)) {
            LOGGER.error("Empty parameters: " + dto);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Trainer trainer = trainerService.getByUsername(dto.getUsername());
        if (trainer == null) {
            LOGGER.error("Trainer not found: username=" + dto.getUsername());
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
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
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainerTrainingResponse>> getTrainings(@RequestParam String username,
                                                                      @RequestParam(required = false) LocalDate periodFrom,
                                                                      @RequestParam(required = false) LocalDate periodTo,
                                                                      @RequestParam(required = false) String trainerName,
                                                                      @RequestParam(required = false) String trainingType) {


        TrainingFiltersDto filter = new TrainingFiltersDto(username, periodFrom, periodTo, trainerName, trainingType);

        List<TrainingResponse> trainings = trainerService.getByCriteria(filter);

        List<TrainerTrainingResponse> traineeTrainingsResponse = trainings.stream().map((training) -> {
            TrainerTrainingResponse response = new TrainerTrainingResponse();
            response.setDate(training.getDate());
            response.setType(training.getType());
            response.setName(training.getName());
            response.setDuration(training.getDuration());
            response.setTraineeUsername(training.getTraineeUsername());
            return response;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(traineeTrainingsResponse);
    }

    @PatchMapping
    public ResponseEntity<String> changeStatus(@RequestParam String username, @RequestParam Boolean isActive) {

        if (username == null || isActive == null) {
            LOGGER.error("Empty parameters");
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(400));
        }

        Boolean isChanged = trainerService.changeStatus(username, isActive);
        if (!isChanged) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok("Success");

    }

    private boolean isValidTrainerDtoForUpdating(TrainerUpdateDto dto) {
        return dto.getUsername() != null && dto.getFirstName() != null
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