package uz.utkirbek.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceHealth")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> getHealth(){
        return ResponseEntity.ok("Service is up");
    }
}
