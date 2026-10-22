package ar.edu.metodologia.solicitudes.repository;

import ar.edu.metodologia.solicitudes.database.DatabaseConnection;
import ar.edu.metodologia.solicitudes.model.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepository {

    public Estudiante guardar(Estudiante estudiante) {
        String sql = """
                INSERT INTO estudiante (nombre, email, documento)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, estudiante.getNombre());
            statement.setString(2, estudiante.getEmail());
            statement.setString(3, estudiante.getDocumento());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    estudiante.setId(keys.getLong(1));
                }
            }

            return estudiante;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar estudiante", e);
        }
    }

    public Estudiante buscarPorId(Long id) {
        String sql = """
                SELECT id, nombre, email, documento
                FROM estudiante
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearEstudiante(resultSet);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar estudiante", e);
        }
    }

    public List<Estudiante> buscarTodos() {
        String sql = """
                SELECT id, nombre, email, documento
                FROM estudiante
                ORDER BY nombre
                """;

        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                estudiantes.add(mapearEstudiante(resultSet));
            }

            return estudiantes;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener estudiantes", e);
        }
    }

    private Estudiante mapearEstudiante(ResultSet resultSet) throws SQLException {
        return new Estudiante(
                resultSet.getLong("id"),
                resultSet.getString("nombre"),
                resultSet.getString("email"),
                resultSet.getString("documento")
        );
    }
}