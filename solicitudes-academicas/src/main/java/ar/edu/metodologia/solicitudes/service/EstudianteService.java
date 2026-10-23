package ar.edu.metodologia.solicitudes.service;

import ar.edu.metodologia.solicitudes.model.Estudiante;
import ar.edu.metodologia.solicitudes.repository.EstudianteRepository;

import java.util.List;

public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService() {
        this.estudianteRepository = new EstudianteRepository();
    }

    public Estudiante registrarEstudiante(
            String nombre,
            String email,
            String documento) {

        ValidacionesServicio.validarCampoObligatorio(nombre, "El nombre es obligatorio.");
        ValidacionesServicio.validarCampoObligatorio(email, "El email es obligatorio.");
        ValidacionesServicio.validarCampoObligatorio(documento, "El documento es obligatorio.");

        Estudiante estudiante = new Estudiante(
                null,
                nombre,
                email,
                documento
        );

        return estudianteRepository.guardar(estudiante);
    }

    public Estudiante buscarEstudiante(Long id) {
        return estudianteRepository.buscarPorId(id);
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteRepository.buscarTodos();
    }
}