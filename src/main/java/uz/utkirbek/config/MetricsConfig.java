package uz.utkirbek.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricsConfig {

    private final Counter customCounter;

    public MetricsConfig(MeterRegistry registry) {
        customCounter = Counter.builder("counter")
                .description("my custom counter")
                .register(registry);

    }

    public void incrementCustomCounter() {
        customCounter.increment();
    }

}
