package ar.edu.metodologia.solicitudes.repository;

import ar.edu.metodologia.solicitudes.model.Estudiante;

public interface EstudianteRepositoryPort {

    Estudiante buscarPorId(Long id);
}
