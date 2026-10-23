package ar.edu.metodologia.solicitudes.service;

import ar.edu.metodologia.solicitudes.model.Estado;
import ar.edu.metodologia.solicitudes.model.Estudiante;
import ar.edu.metodologia.solicitudes.model.Prioridad;
import ar.edu.metodologia.solicitudes.model.Responsable;
import ar.edu.metodologia.solicitudes.model.Solicitud;
import ar.edu.metodologia.solicitudes.model.TipoSolicitud;
import ar.edu.metodologia.solicitudes.repository.EstudianteRepository;
import ar.edu.metodologia.solicitudes.repository.ResponsableRepository;
import ar.edu.metodologia.solicitudes.repository.SolicitudRepository;

import java.time.LocalDateTime;
import java.util.List;

public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final EstudianteRepository estudianteRepository;
    private final ResponsableRepository responsableRepository;

    public SolicitudService() {
        this.solicitudRepository = new SolicitudRepository();
        this.estudianteRepository = new EstudianteRepository();
        this.responsableRepository = new ResponsableRepository();
    }

    public Solicitud registrarSolicitud(
            Long estudianteId,
            TipoSolicitud tipo,
            String descripcion,
            Prioridad prioridad) {

        Estudiante estudiante = estudianteRepository.buscarPorId(estudianteId);

        if (estudiante == null) {
            throw new RuntimeException("El estudiante no existe.");
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
            throw new RuntimeException("La solicitud no existe.");
        }

        Responsable responsable = responsableRepository.buscarPorId(responsableId);

        if (responsable == null) {
            throw new RuntimeException("El responsable no existe.");
        }

        solicitud.setResponsable(responsable);

        actualizarResponsable(solicitud);
    }

    public void cambiarEstado(Long solicitudId, Estado nuevoEstado) {

        Solicitud solicitud = solicitudRepository.buscarPorId(solicitudId);

        if (solicitud == null) {
            throw new RuntimeException("La solicitud no existe.");
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
            throw new RuntimeException(
                    "Una solicitud cancelada no puede continuar procesándose."
            );
        }

        if (solicitud.getEstado() == Estado.RESUELTA
                && nuevoEstado == Estado.PENDIENTE) {

            throw new RuntimeException(
                    "Una solicitud resuelta no puede volver a pendiente."
            );
        }

        if (nuevoEstado == Estado.RESUELTA
                && solicitud.getResponsable() == null) {

            throw new RuntimeException(
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