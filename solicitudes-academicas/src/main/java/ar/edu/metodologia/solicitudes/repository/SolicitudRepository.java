package ar.edu.metodologia.solicitudes.repository;

import ar.edu.metodologia.solicitudes.database.DatabaseConnection;
import ar.edu.metodologia.solicitudes.model.Estado;
import ar.edu.metodologia.solicitudes.model.Estudiante;
import ar.edu.metodologia.solicitudes.model.Prioridad;
import ar.edu.metodologia.solicitudes.model.Responsable;
import ar.edu.metodologia.solicitudes.model.Solicitud;
import ar.edu.metodologia.solicitudes.model.TipoSolicitud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepository {

    public Solicitud guardar(Solicitud solicitud) {
        String sql = """
                INSERT INTO solicitud (
                    estudiante_id,
                    tipo,
                    descripcion,
                    prioridad,
                    estado,
                    responsable_id,
                    fecha_creacion
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, solicitud.getEstudiante().getId());
            statement.setString(2, solicitud.getTipo().name());
            statement.setString(3, solicitud.getDescripcion());
            statement.setString(4, solicitud.getPrioridad().name());
            statement.setString(5, solicitud.getEstado().name());

            if (solicitud.getResponsable() != null) {
                statement.setLong(6, solicitud.getResponsable().getId());
            } else {
                statement.setNull(6, java.sql.Types.BIGINT);
            }

            statement.setTimestamp(
                    7,
                    Timestamp.valueOf(solicitud.getFechaCreacion())
            );

            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    solicitud.setId(keys.getLong(1));
                }
            }

            return solicitud;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar solicitud", e);
        }
    }

    public Solicitud buscarPorId(Long id) {
        String sql = """
                SELECT
                    s.id,
                    s.tipo,
                    s.descripcion,
                    s.prioridad,
                    s.estado,
                    s.fecha_creacion,

                    e.id AS estudiante_id,
                    e.nombre AS estudiante_nombre,
                    e.email AS estudiante_email,
                    e.documento AS estudiante_documento,

                    r.id AS responsable_id,
                    r.nombre AS responsable_nombre,
                    r.area AS responsable_area,
                    r.email AS responsable_email

                FROM solicitud s
                INNER JOIN estudiante e
                    ON s.estudiante_id = e.id
                LEFT JOIN responsable r
                    ON s.responsable_id = r.id
                WHERE s.id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapearSolicitud(resultSet);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar solicitud", e);
        }
    }

    public List<Solicitud> buscarTodas() {
        String sql = """
                SELECT
                    s.id,
                    s.tipo,
                    s.descripcion,
                    s.prioridad,
                    s.estado,
                    s.fecha_creacion,

                    e.id AS estudiante_id,
                    e.nombre AS estudiante_nombre,
                    e.email AS estudiante_email,
                    e.documento AS estudiante_documento,

                    r.id AS responsable_id,
                    r.nombre AS responsable_nombre,
                    r.area AS responsable_area,
                    r.email AS responsable_email

                FROM solicitud s
                INNER JOIN estudiante e
                    ON s.estudiante_id = e.id
                LEFT JOIN responsable r
                    ON s.responsable_id = r.id
                ORDER BY s.fecha_creacion DESC
                """;

        List<Solicitud> solicitudes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                solicitudes.add(mapearSolicitud(resultSet));
            }

            return solicitudes;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener solicitudes", e);
        }
    }

    private Solicitud mapearSolicitud(ResultSet resultSet) throws SQLException {

        Estudiante estudiante = new Estudiante(
                resultSet.getLong("estudiante_id"),
                resultSet.getString("estudiante_nombre"),
                resultSet.getString("estudiante_email"),
                resultSet.getString("estudiante_documento")
        );

        Responsable responsable = null;

        Long responsableId = resultSet.getObject("responsable_id", Long.class);

        if (responsableId != null) {
            responsable = new Responsable(
                    responsableId,
                    resultSet.getString("responsable_nombre"),
                    resultSet.getString("responsable_area"),
                    resultSet.getString("responsable_email")
            );
        }

        return new Solicitud(
                resultSet.getLong("id"),
                estudiante,
                TipoSolicitud.valueOf(resultSet.getString("tipo")),
                resultSet.getString("descripcion"),
                Prioridad.valueOf(resultSet.getString("prioridad")),
                Estado.valueOf(resultSet.getString("estado")),
                responsable,
                resultSet.getTimestamp("fecha_creacion").toLocalDateTime()
        );
    }

    public void actualizarResponsable(Solicitud solicitud) {
        String sql = """
            UPDATE solicitud
            SET responsable_id = ?
            WHERE id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (solicitud.getResponsable() != null) {
                statement.setLong(1, solicitud.getResponsable().getId());
            } else {
                statement.setNull(1, java.sql.Types.BIGINT);
            }

            statement.setLong(2, solicitud.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar responsable", e);
        }
    }

    public void actualizarEstado(Solicitud solicitud) {
        String sql = """
            UPDATE solicitud
            SET estado = ?
            WHERE id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, solicitud.getEstado().name());
            statement.setLong(2, solicitud.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado", e);
        }
    }
}