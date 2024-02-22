package uz.utkirbek.health;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@PropertySource("classpath:database.properties")
public class DatabaseHealth implements HealthIndicator {

    @Value("${db.url}")
    private String jdbcUrl;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Override
    public Health health() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            return Health.up().withDetail("Database status", "Database is available").build();
        } catch (SQLException e) {
            return Health.down().withDetail("Database status", "Database is not available").build();
        }
    }
}
