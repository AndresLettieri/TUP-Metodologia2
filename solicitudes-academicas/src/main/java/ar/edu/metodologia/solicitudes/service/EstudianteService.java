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

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("El email es obligatorio.");
        }

        if (documento == null || documento.trim().isEmpty()) {
            throw new RuntimeException("El documento es obligatorio.");
        }

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