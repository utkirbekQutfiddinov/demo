package uz.utkirbek.controller;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.utkirbek.model.dto.UserChangePasswordDto;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final Counter userApiCounter =
            Counter.build()
                    .name("userApiCounter")
                    .help("Total number of calls to the UserController's APIs")
                    .register();


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {

        try {
            userApiCounter.inc();
            if (username == null || password == null) {
                LOGGER.info("Empty parameters: username=" + username + ", password=" + password);
                return ResponseEntity.badRequest().body(null);
            }

            User user = userService.findByUsernameAndPassword(username, password);

            if (user == null) {
                LOGGER.info("User does not exist: username=" + username + ", password=" + password);
                return ResponseEntity.notFound().build();
            }

            if (!user.isActive()) {
                LOGGER.info("User is not active");
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            LOGGER.error("error on login: " + username + ", pass:" + password);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto dto) {

        try {
            userApiCounter.inc();
            if (dto.getUsername() == null || dto.getOldPassword() == null || dto.getNewPassword() == null) {
                LOGGER.info("Empty parameters: " + dto);
                return ResponseEntity.badRequest().body(null);
            }

            User user = userService.findByUsernameAndPassword(dto.getUsername(), dto.getOldPassword());

            if (user == null) {
                LOGGER.info("User does not exist: " + dto);
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }

            Boolean hasChanged = userService.changePassword(user.getId(), dto.getNewPassword());

            if (!hasChanged) {
                LOGGER.error("Password changing failure: " + dto);
                return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error: " + dto);
            return ResponseEntity.internalServerError().build();
        }

    }
}
