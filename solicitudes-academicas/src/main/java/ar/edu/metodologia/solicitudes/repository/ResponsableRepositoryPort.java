package ar.edu.metodologia.solicitudes.repository;

import ar.edu.metodologia.solicitudes.model.Responsable;

public interface ResponsableRepositoryPort {

    Responsable buscarPorId(Long id);
}
