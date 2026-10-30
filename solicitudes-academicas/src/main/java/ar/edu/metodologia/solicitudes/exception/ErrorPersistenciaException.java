package ar.edu.metodologia.solicitudes.exception;

public final class ErrorPersistenciaException extends ErrorAplicacionException {

    public ErrorPersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
