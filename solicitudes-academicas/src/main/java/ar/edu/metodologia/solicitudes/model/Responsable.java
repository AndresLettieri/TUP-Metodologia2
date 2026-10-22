package ar.edu.metodologia.solicitudes.model;

public class Responsable {

    private Long id;
    private String nombre;
    private String area;
    private String email;

    public Responsable() {
    }

    public Responsable(Long id, String nombre, String area, String email) {
        this.id = id;
        this.nombre = nombre;
        this.area = area;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}