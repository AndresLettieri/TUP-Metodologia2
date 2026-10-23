package ar.edu.metodologia.solicitudes;

import ar.edu.metodologia.solicitudes.database.DatabaseInitializer;
import ar.edu.metodologia.solicitudes.model.Estado;
import ar.edu.metodologia.solicitudes.model.Estudiante;
import ar.edu.metodologia.solicitudes.model.Prioridad;
import ar.edu.metodologia.solicitudes.model.Responsable;
import ar.edu.metodologia.solicitudes.model.Solicitud;
import ar.edu.metodologia.solicitudes.model.TipoSolicitud;
import ar.edu.metodologia.solicitudes.service.EstudianteService;
import ar.edu.metodologia.solicitudes.service.ResponsableService;
import ar.edu.metodologia.solicitudes.service.SolicitudService;

import java.util.List;

public class Main {

    private static final EstudianteService estudianteService =
            new EstudianteService();

    private static final ResponsableService responsableService =
            new ResponsableService();

    private static final SolicitudService solicitudService =
            new SolicitudService();

    private static final Consola consola = new Consola();

    public static void main(String[] args) {

        inicializarBase();

        int opcion;

        do {
            consola.mostrarMenu();
            opcion = consola.leerEntero("Seleccione una opción: ");

            try {
                ejecutarOpcion(opcion);
            } catch (RuntimeException e) {
                System.out.println();
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);

        System.out.println();
        System.out.println("Sistema finalizado.");
    }

    private static void inicializarBase() {

        System.out.println("Sistema de Gestión de Solicitudes Académicas");
        System.out.println("--------------------------------------------");

        if (!DatabaseInitializer.existeBase()) {
            System.out.println("Inicializando base de datos...");
            DatabaseInitializer.initialize();
            System.out.println("Base de datos creada correctamente.");
        } else {
            System.out.println("Base de datos existente.");
        }

        System.out.println();
    }

    private static void ejecutarOpcion(int opcion) {

        switch (opcion) {

            case 1:
                registrarEstudiante();
                break;

            case 2:
                registrarResponsable();
                break;

            case 3:
                registrarSolicitud();
                break;

            case 4:
                asignarResponsable();
                break;

            case 5:
                cambiarEstado();
                break;

            case 6:
                listarEstudiantes();
                break;

            case 7:
                listarResponsables();
                break;

            case 8:
                listarSolicitudes();
                break;

            case 0:
                break;

            default:
                System.out.println("Opción inválida.");
        }
    }

    private static void registrarEstudiante() {

        System.out.println();
        System.out.println("--- Registrar estudiante ---");

        String nombre = consola.leerTexto("Nombre: ");
        String email = consola.leerTexto("Email: ");
        String documento = consola.leerTexto("Documento: ");

        Estudiante estudiante = estudianteService.registrarEstudiante(
                nombre,
                email,
                documento
        );

        System.out.println(
                "Estudiante registrado correctamente. ID: "
                        + estudiante.getId()
        );
    }

    private static void registrarResponsable() {

        System.out.println();
        System.out.println("--- Registrar responsable ---");

        String nombre = consola.leerTexto("Nombre: ");
        String area = consola.leerTexto("Área: ");
        String email = consola.leerTexto("Email: ");

        Responsable responsable = responsableService.registrarResponsable(
                nombre,
                area,
                email
        );

        System.out.println(
                "Responsable registrado correctamente. ID: "
                        + responsable.getId()
        );
    }

    private static void registrarSolicitud() {

        System.out.println();
        System.out.println("--- Registrar solicitud ---");

        Long estudianteId = consola.leerLong("ID del estudiante: ");

        TipoSolicitud tipo = consola.seleccionarTipoSolicitud();

        String descripcion = consola.leerTexto("Descripción: ");

        Prioridad prioridad = consola.seleccionarPrioridad();

        Solicitud solicitud = solicitudService.registrarSolicitud(
                estudianteId,
                tipo,
                descripcion,
                prioridad
        );

        System.out.println(
                "Solicitud registrada correctamente. ID: "
                        + solicitud.getId()
        );
    }

    private static void asignarResponsable() {

        System.out.println();
        System.out.println("--- Asignar responsable ---");

        Long solicitudId = consola.leerLong("ID de la solicitud: ");
        Long responsableId = consola.leerLong("ID del responsable: ");

        solicitudService.asignarResponsable(
                solicitudId,
                responsableId
        );

        System.out.println("Responsable asignado correctamente.");
    }

    private static void cambiarEstado() {

        System.out.println();
        System.out.println("--- Cambiar estado ---");

        Long solicitudId = consola.leerLong("ID de la solicitud: ");

        Estado nuevoEstado = consola.seleccionarEstado();

        solicitudService.cambiarEstado(
                solicitudId,
                nuevoEstado
        );

        System.out.println("Estado actualizado correctamente.");
    }

    private static void listarEstudiantes() {

        System.out.println();
        System.out.println("--- Estudiantes ---");

        List<Estudiante> estudiantes =
                estudianteService.listarEstudiantes();

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        for (Estudiante estudiante : estudiantes) {
            System.out.println(
                    estudiante.getId() + " - "
                            + estudiante.getNombre()
                            + " - Documento: "
                            + estudiante.getDocumento()
                            + " - Email: "
                            + estudiante.getEmail()
            );
        }
    }

    private static void listarResponsables() {

        System.out.println();
        System.out.println("--- Responsables ---");

        List<Responsable> responsables =
                responsableService.listarResponsables();

        if (responsables.isEmpty()) {
            System.out.println("No hay responsables registrados.");
            return;
        }

        for (Responsable responsable : responsables) {
            System.out.println(
                    responsable.getId() + " - "
                            + responsable.getNombre()
                            + " - Área: "
                            + responsable.getArea()
                            + " - Email: "
                            + responsable.getEmail()
            );
        }
    }

    private static void listarSolicitudes() {

        System.out.println();
        System.out.println("--- Solicitudes ---");

        List<Solicitud> solicitudes =
                solicitudService.buscarSolicitudes();

        if (solicitudes.isEmpty()) {
            System.out.println("No hay solicitudes registradas.");
            return;
        }

        for (Solicitud solicitud : solicitudes) {

            String responsable = solicitud.getResponsable() != null
                    ? solicitud.getResponsable().getNombre()
                    : "Sin asignar";

            System.out.println(
                    "ID: " + solicitud.getId()
                            + " | Estudiante: "
                            + solicitud.getEstudiante().getNombre()
                            + " | Tipo: "
                            + solicitud.getTipo()
                            + " | Prioridad: "
                            + solicitud.getPrioridad()
                            + " | Estado: "
                            + solicitud.getEstado()
                            + " | Responsable: "
                            + responsable
            );
        }
    }
}