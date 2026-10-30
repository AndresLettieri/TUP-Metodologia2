package ar.edu.metodologia.solicitudes.model;

public class NuevaSolicitud {

    private final Long estudianteId;
    private final TipoSolicitud tipo;
    private final String descripcion;
    private final Prioridad prioridad;

    public NuevaSolicitud(
            Long estudianteId,
            TipoSolicitud tipo,
            String descripcion,
            Prioridad prioridad) {
        this.estudianteId = estudianteId;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }
}