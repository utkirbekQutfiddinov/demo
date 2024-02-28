package uz.utkirbek.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Component
public class ServiceHealth implements HealthIndicator {
    private static final Logger logger = Logger.getLogger(ServiceHealth.class.getName());

    private static final String EXTERNAL_SERVICE_URL = "http://localhost:8082/serviceHealth";
    private static final String REQUEST_METHOD_NAME = "GET";


    @Override
    public Health health() {
        boolean isHealthy = isExternalServiceUp();

        if (isHealthy) {
            return Health.up().withDetail("WebServiceHealth", "Service is available").build();
        } else {
            return Health.down().withDetail("WebServiceHealth", "Service is not available").build();
        }
    }

    private boolean isExternalServiceUp() {
        try {
            URL url = new URL(EXTERNAL_SERVICE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD_NAME);
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            logger.warning("Exception on: " + e.getMessage());
            return false;
        }
    }
}
