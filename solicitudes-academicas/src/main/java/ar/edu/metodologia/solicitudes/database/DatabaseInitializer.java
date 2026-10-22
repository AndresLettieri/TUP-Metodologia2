package ar.edu.metodologia.solicitudes.database;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {

    public static boolean existeBase() {
        String sql = """
                SELECT COUNT(*)
                FROM INFORMATION_SCHEMA.TABLES
                WHERE TABLE_NAME = 'ESTUDIANTE'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

            return false;

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar la base de datos", e);
        }
    }

    public static void initialize() {
        try (
                Connection connection = DatabaseConnection.getConnection();
                InputStream inputStream = DatabaseInitializer.class
                        .getClassLoader()
                        .getResourceAsStream("schema.sql")
        ) {
            if (inputStream == null) {
                throw new RuntimeException("No se encontró el archivo schema.sql");
            }

            StringBuilder script = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    script.append(line).append("\n");
                }
            }

            String[] statements = script.toString().split(";");

            for (String sql : statements) {
                if (!sql.trim().isEmpty()) {
                    try (Statement statement = connection.createStatement()) {
                        statement.execute(sql);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }
}