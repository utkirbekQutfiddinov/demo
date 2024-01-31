package uz.utkirbek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.utkirbek.model.dto.UserChangePasswordDto;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password) {

        if (username == null || password == null) {
            LOGGER.error("Empty parameters: username=" + username + ", password=" + password);
            return ResponseEntity.badRequest().body(null);
        }

        User user = userService.findByUsernameAndPassword(username, password);

        if (user == null) {
            LOGGER.error("User does not exist: username=" + username + ", password=" + password);
            return ResponseEntity.notFound().build();
        }

        if (!user.isActive()) {
            LOGGER.error("User is not active");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Success");
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordDto dto) {

        if (dto.getUsername() == null || dto.getOldPassword() == null || dto.getNewPassword() == null) {
            LOGGER.error("Empty parameters: " + dto);
            return ResponseEntity.badRequest().body(null);
        }

        User user = userService.findByUsernameAndPassword(dto.getUsername(), dto.getOldPassword());

        if (user == null) {
            LOGGER.error("User does not exist: " + dto);
            return new ResponseEntity<>("User does not exist", HttpStatusCode.valueOf(404));
        }

        Boolean hasChanged = userService.changePassword(user.getId(), dto.getNewPassword());

        if (!hasChanged) {
            LOGGER.error("Password changing failure: " + dto);
            return new ResponseEntity<>("Error", HttpStatusCode.valueOf(404));
        }

        return new ResponseEntity<>("Success", HttpStatusCode.valueOf(200));

    }
}
