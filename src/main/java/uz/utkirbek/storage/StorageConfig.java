package uz.utkirbek.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uz.utkirbek.enums.Table;
import uz.utkirbek.model.BaseIdBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class StorageConfig {

    @Bean
    @Scope("singleton")
    public Map<Table, List<BaseIdBean>> initializeStorage() {
        return new HashMap<>();
    }

}
