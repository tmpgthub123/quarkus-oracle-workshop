package com.enlight.demo.db;

import com.enlight.demo.common.DemoDatabaseException;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Small helper that reads SQL files from classpath and executes them with JDBC.
 * This keeps startup logic explicit and easy for a live workshop.
 */
@ApplicationScoped
public class SqlScriptExecutor {

    public void runScripts(DataSource dataSource, String... resourcePaths) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            // Keep auto-commit on so one-time bootstrap statements are visible immediately.
            connection.setAutoCommit(true);

            for (String resourcePath : resourcePaths) {
                String sql = readResource(resourcePath);
                if (sql == null || sql.isBlank()) {
                    continue;
                }
                statement.execute(sql);
            }
        } catch (SQLException | IOException exception) {
            throw new DemoDatabaseException("Failed to execute SQL bootstrap scripts", exception);
        }
    }

    private String readResource(String path) throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("Missing SQL resource: " + path);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8).trim();
        }
    }
}
