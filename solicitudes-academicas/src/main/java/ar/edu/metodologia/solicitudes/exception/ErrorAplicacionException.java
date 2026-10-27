package ar.edu.metodologia.solicitudes.exception;

public class ErrorAplicacionException extends RuntimeException {

    public ErrorAplicacionException(String mensaje) {
        super(mensaje);
    }

    public ErrorAplicacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
