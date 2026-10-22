package ar.edu.metodologia.solicitudes.service;

import ar.edu.metodologia.solicitudes.model.Responsable;
import ar.edu.metodologia.solicitudes.repository.ResponsableRepository;

import java.util.List;

public class ResponsableService {

    private final ResponsableRepository responsableRepository;

    public ResponsableService() {
        this.responsableRepository = new ResponsableRepository();
    }

    public Responsable registrarResponsable(
            String nombre,
            String area,
            String email) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio.");
        }

        if (area == null || area.trim().isEmpty()) {
            throw new RuntimeException("El área es obligatoria.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("El email es obligatorio.");
        }

        Responsable responsable = new Responsable(
                null,
                nombre,
                area,
                email
        );

        return responsableRepository.guardar(responsable);
    }

    public Responsable buscarResponsable(Long id) {
        return responsableRepository.buscarPorId(id);
    }

    public List<Responsable> listarResponsables() {
        return responsableRepository.buscarTodos();
    }
}