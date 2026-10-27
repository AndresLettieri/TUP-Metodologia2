package ar.edu.metodologia.solicitudes.service;

import ar.edu.metodologia.solicitudes.exception.ErrorValidacionException;

final class ValidacionesServicio {

    private ValidacionesServicio() {
    }

    static void validarCampoObligatorio(String valor, String mensajeError) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ErrorValidacionException(mensajeError);
        }
    }
}
