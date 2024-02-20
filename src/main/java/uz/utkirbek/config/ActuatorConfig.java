package uz.utkirbek.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ActuatorConfig implements HealthIndicator {

    private static final String EXTERNAL_SERVICE_URL = "http://localhost:8082";

    @Override
    public Health health() {
        boolean isHealthy = isExternalServiceUp();

        if (isHealthy) {
            return Health.up().withDetail("CustomStatus", "External service is available").build();
        } else {
            return Health.down().withDetail("CustomStatus", "External service is not available").build();
        }
    }

    private boolean isExternalServiceUp() {
        try {
            URL url = new URL(EXTERNAL_SERVICE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }
}
