package ar.edu.metodologia.solicitudes.repository;

import ar.edu.metodologia.solicitudes.model.Solicitud;

import java.util.List;

public interface SolicitudRepositoryPort {

    Solicitud guardar(Solicitud solicitud);

    Solicitud buscarPorId(Long id);

    List<Solicitud> buscarTodas();

    void actualizarResponsable(Solicitud solicitud);

    void actualizarEstado(Solicitud solicitud);
}
