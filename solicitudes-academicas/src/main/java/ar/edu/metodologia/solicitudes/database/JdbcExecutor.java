package ar.edu.metodologia.solicitudes.database;

import ar.edu.metodologia.solicitudes.exception.ErrorPersistenciaException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public final class JdbcExecutor {

    private JdbcExecutor() {
    }

    public static <T> T ejecutar(
            String mensajeError,
            OperacionJdbc<T> operacion) {
        Objects.requireNonNull(operacion, "operacion no puede ser null");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return operacion.ejecutar(connection);
        } catch (SQLException e) {
            throw new ErrorPersistenciaException(mensajeError, e);
        }
    }

    @FunctionalInterface
    public interface OperacionJdbc<T> {

        T ejecutar(Connection connection) throws SQLException;
    }
}
