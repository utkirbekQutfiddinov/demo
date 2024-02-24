package uz.utkirbek.health;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DatabaseHealthTests {

    @InjectMocks
    private DatabaseHealth databaseHealth;

    @Mock
    private DriverManager mockDriverManager;

    @Test
    public void testDatabaseUp() throws SQLException {

        ReflectionTestUtils.setField(databaseHealth, "jdbcUrl", "jdbc:postgres://localhost:5432/postgres");
        ReflectionTestUtils.setField(databaseHealth, "username", "postgres");
        ReflectionTestUtils.setField(databaseHealth, "password", "1223");

        when(mockDriverManager.getConnection(any(String.class), any(String.class), any(String.class))).thenReturn(Mockito.mock(Connection.class));

        Health result = databaseHealth.health();

        assertEquals(Health.up(), result.getStatus());
        assertEquals("Database is available", result.getDetails().get("Database status"));
    }

    @Test
    public void testDatabaseDown() throws SQLException {
        // Mocking data for a failed connection
        when(mockDriverManager.getConnection(any(String.class), any(String.class), any(String.class))).thenThrow(new SQLException("Connection error"));

        ReflectionTestUtils.setField(databaseHealth, "jdbcUrl", "jdbc:postgres://localhost:5432/postgres");
        ReflectionTestUtils.setField(databaseHealth, "username", "postgres");
        ReflectionTestUtils.setField(databaseHealth, "password", "1223");

        // Calling the health method
        Health result = databaseHealth.health();

        // Assertions
        assertEquals(Health.down(), result.getStatus());
        assertEquals("Database is not available", result.getDetails().get("Database status"));
    }
}
