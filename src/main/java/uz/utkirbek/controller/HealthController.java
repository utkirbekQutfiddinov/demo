package uz.utkirbek.controller;

import io.prometheus.client.Counter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceHealth")
public class HealthController {
    private static final Counter healthControllerApiCounter =
            Counter.build()
                    .name("healthControllerApiCounter")
                    .help("Total number of calls to the HealthController's APIs")
                    .register();

    @GetMapping
    public ResponseEntity<String> getHealth() {
        healthControllerApiCounter.inc();
        return ResponseEntity.ok("Service is up");
    }
}
