package ar.edu.metodologia.solicitudes.service;

final class ValidacionesServicio {

    private ValidacionesServicio() {
    }

    static void validarCampoObligatorio(String valor, String mensajeError) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new RuntimeException(mensajeError);
        }
    }
}
