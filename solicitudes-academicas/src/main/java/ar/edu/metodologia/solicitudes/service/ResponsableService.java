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

        ValidacionesServicio.validarCampoObligatorio(nombre, "El nombre es obligatorio.");
        ValidacionesServicio.validarCampoObligatorio(area, "El área es obligatoria.");
        ValidacionesServicio.validarCampoObligatorio(email, "El email es obligatorio.");

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