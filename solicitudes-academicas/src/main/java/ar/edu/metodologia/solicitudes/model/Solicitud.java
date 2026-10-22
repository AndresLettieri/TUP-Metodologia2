package ar.edu.metodologia.solicitudes.model;

import java.time.LocalDateTime;

public class Solicitud {

    private Long id;
    private Estudiante estudiante;
    private TipoSolicitud tipo;
    private String descripcion;
    private Prioridad prioridad;
    private Estado estado;
    private Responsable responsable;
    private LocalDateTime fechaCreacion;

    public Solicitud() {
    }

    public Solicitud(
            Long id,
            Estudiante estudiante,
            TipoSolicitud tipo,
            String descripcion,
            Prioridad prioridad,
            Estado estado,
            Responsable responsable,
            LocalDateTime fechaCreacion) {

        this.id = id;
        this.estudiante = estudiante;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.responsable = responsable;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public void setTipo(TipoSolicitud tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}