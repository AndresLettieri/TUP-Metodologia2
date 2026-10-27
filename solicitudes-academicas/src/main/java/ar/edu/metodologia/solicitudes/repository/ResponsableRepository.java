package ar.edu.metodologia.solicitudes.repository;

import ar.edu.metodologia.solicitudes.database.DatabaseConnection;
import ar.edu.metodologia.solicitudes.exception.ErrorPersistenciaException;
import ar.edu.metodologia.solicitudes.model.Responsable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResponsableRepository {

    public Responsable guardar(Responsable responsable) {
        String sql = """
                INSERT INTO responsable (nombre, area, email)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, responsable.getNombre());
            statement.setString(2, responsable.getArea());
            statement.setString(3, responsable.getEmail());

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    responsable.setId(keys.getLong(1));
                }
            }

            return responsable;

        } catch (SQLException e) {
            throw new ErrorPersistenciaException("Error al guardar responsable", e);
        }
    }

    public Responsable buscarPorId(Long id) {
        String sql = """
                SELECT id, nombre, area, email
                FROM responsable
                WHERE id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearResponsable(resultSet);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new ErrorPersistenciaException("Error al buscar responsable", e);
        }
    }

    public List<Responsable> buscarTodos() {
        String sql = """
                SELECT id, nombre, area, email
                FROM responsable
                ORDER BY nombre
                """;

        List<Responsable> responsables = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                responsables.add(mapearResponsable(resultSet));
            }

            return responsables;

        } catch (SQLException e) {
            throw new ErrorPersistenciaException("Error al obtener responsables", e);
        }
    }

    private Responsable mapearResponsable(ResultSet resultSet) throws SQLException {
        return new Responsable(
                resultSet.getLong("id"),
                resultSet.getString("nombre"),
                resultSet.getString("area"),
                resultSet.getString("email")
        );
    }
}
