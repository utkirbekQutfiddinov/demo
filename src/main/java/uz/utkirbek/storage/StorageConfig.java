package uz.utkirbek.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class StorageConfig {

    @Bean
    public Map<String, List> initializeStorage(){
        return new HashMap<>();
    }
}
