package ar.edu.metodologia.solicitudes.service;

import ar.edu.metodologia.solicitudes.exception.EntidadNoEncontradaException;
import ar.edu.metodologia.solicitudes.exception.ReglaNegocioException;
import ar.edu.metodologia.solicitudes.model.Estado;
import ar.edu.metodologia.solicitudes.model.Estudiante;
import ar.edu.metodologia.solicitudes.model.Prioridad;
import ar.edu.metodologia.solicitudes.model.Responsable;
import ar.edu.metodologia.solicitudes.model.Solicitud;
import ar.edu.metodologia.solicitudes.model.TipoSolicitud;
import ar.edu.metodologia.solicitudes.repository.EstudianteRepositoryPort;
import ar.edu.metodologia.solicitudes.repository.ResponsableRepositoryPort;
import ar.edu.metodologia.solicitudes.repository.SolicitudRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class SolicitudService {

    private final SolicitudRepositoryPort solicitudRepository;
    private final EstudianteRepositoryPort estudianteRepository;
    private final ResponsableRepositoryPort responsableRepository;

    public SolicitudService(
            SolicitudRepositoryPort solicitudRepository,
            EstudianteRepositoryPort estudianteRepository,
            ResponsableRepositoryPort responsableRepository) {
        this.solicitudRepository = Objects.requireNonNull(
                solicitudRepository,
                "solicitudRepository no puede ser null"
        );
        this.estudianteRepository = Objects.requireNonNull(
                estudianteRepository,
                "estudianteRepository no puede ser null"
        );
        this.responsableRepository = Objects.requireNonNull(
                responsableRepository,
                "responsableRepository no puede ser null"
        );
    }

    public Solicitud registrarSolicitud(
            Long estudianteId,
            TipoSolicitud tipo,
            String descripcion,
            Prioridad prioridad) {

        Estudiante estudiante = estudianteRepository.buscarPorId(estudianteId);

        if (estudiante == null) {
            throw new EntidadNoEncontradaException("El estudiante no existe.");
        }

        ValidacionesServicio.validarCampoObligatorio(descripcion, "La descripción es obligatoria.");

        Solicitud solicitud = new Solicitud(
                null,
                estudiante,
                tipo,
                descripcion,
                prioridad,
                Estado.PENDIENTE,
                null,
                LocalDateTime.now()
        );

        return solicitudRepository.guardar(solicitud);
    }

    public void asignarResponsable(Long solicitudId, Long responsableId) {

        Solicitud solicitud = solicitudRepository.buscarPorId(solicitudId);

        if (solicitud == null) {
            throw new EntidadNoEncontradaException("La solicitud no existe.");
        }

        Responsable responsable = responsableRepository.buscarPorId(responsableId);

        if (responsable == null) {
            throw new EntidadNoEncontradaException("El responsable no existe.");
        }

        solicitud.setResponsable(responsable);

        actualizarResponsable(solicitud);
    }

    public void cambiarEstado(Long solicitudId, Estado nuevoEstado) {

        Solicitud solicitud = solicitudRepository.buscarPorId(solicitudId);

        if (solicitud == null) {
            throw new EntidadNoEncontradaException("La solicitud no existe.");
        }

        validarCambioEstado(solicitud, nuevoEstado);

        solicitud.setEstado(nuevoEstado);

        actualizarEstado(solicitud);
    }

    public List<Solicitud> buscarSolicitudes() {
        return solicitudRepository.buscarTodas();
    }

    public Solicitud buscarSolicitud(Long id) {
        return solicitudRepository.buscarPorId(id);
    }

    private void validarCambioEstado(
            Solicitud solicitud,
            Estado nuevoEstado) {

        if (solicitud.getEstado() == Estado.CANCELADA) {
            throw new ReglaNegocioException(
                    "Una solicitud cancelada no puede continuar procesándose."
            );
        }

        if (solicitud.getEstado() == Estado.RESUELTA
                && nuevoEstado == Estado.PENDIENTE) {

            throw new ReglaNegocioException(
                    "Una solicitud resuelta no puede volver a pendiente."
            );
        }

        if (nuevoEstado == Estado.RESUELTA
                && solicitud.getResponsable() == null) {

            throw new ReglaNegocioException(
                    "No se puede resolver una solicitud sin responsable."
            );
        }
    }

    private void actualizarResponsable(Solicitud solicitud) {
        solicitudRepository.actualizarResponsable(solicitud);
    }

    private void actualizarEstado(Solicitud solicitud) {
        solicitudRepository.actualizarEstado(solicitud);
    }
}
