package uz.utkirbek.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseHealth implements HealthIndicator {
    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "1223";

    @Override
    public Health health() {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            return Health.up().withDetail("Database status", "Database is available").build();
        } catch (SQLException e) {
            return Health.down().withDetail("Database status", "Database is not available").build();
        }
    }
}
