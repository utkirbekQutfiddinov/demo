package uz.utkirbek.health;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
public class ServiceHealth implements HealthIndicator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceHealth.class.getName());

    private static final String EXTERNAL_SERVICE_URL = "http://localhost:8082/serviceHealth";

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
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(EXTERNAL_SERVICE_URL);

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            return statusCode == 200;
        } catch (Exception e) {
            LOGGER.error("Exception on: " + e.getMessage());
            return false;
        }
    }
}
