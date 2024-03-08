package uz.utkirbek.controller;

import io.prometheus.client.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.security.JwtProvider;
import uz.utkirbek.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private static final Counter userApiCounter =
            Counter.build()
                    .name("authApiCounter")
                    .help("Total number of calls to the AuthController's APIs")
                    .register();

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
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

            return ResponseEntity.ok(jwtProvider.generateToken(username));
        } catch (Exception e) {
            LOGGER.error("error on login: " + username + ", pass:" + password);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}
