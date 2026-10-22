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
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final EstudianteService estudianteService =
            new EstudianteService();

    private static final ResponsableService responsableService =
            new ResponsableService();

    private static final SolicitudService solicitudService =
            new SolicitudService();

    public static void main(String[] args) {

        inicializarBase();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");

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

    private static void mostrarMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println(" Sistema de Gestión de Solicitudes");
        System.out.println("========================================");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Registrar responsable");
        System.out.println("3. Registrar solicitud");
        System.out.println("4. Asignar responsable");
        System.out.println("5. Cambiar estado");
        System.out.println("6. Listar estudiantes");
        System.out.println("7. Listar responsables");
        System.out.println("8. Listar solicitudes");
        System.out.println("0. Salir");
        System.out.println("========================================");
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

        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String documento = leerTexto("Documento: ");

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

        String nombre = leerTexto("Nombre: ");
        String area = leerTexto("Área: ");
        String email = leerTexto("Email: ");

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

        Long estudianteId = leerLong("ID del estudiante: ");

        TipoSolicitud tipo = seleccionarTipoSolicitud();

        String descripcion = leerTexto("Descripción: ");

        Prioridad prioridad = seleccionarPrioridad();

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

        Long solicitudId = leerLong("ID de la solicitud: ");
        Long responsableId = leerLong("ID del responsable: ");

        solicitudService.asignarResponsable(
                solicitudId,
                responsableId
        );

        System.out.println("Responsable asignado correctamente.");
    }

    private static void cambiarEstado() {

        System.out.println();
        System.out.println("--- Cambiar estado ---");

        Long solicitudId = leerLong("ID de la solicitud: ");

        Estado nuevoEstado = seleccionarEstado();

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

    private static TipoSolicitud seleccionarTipoSolicitud() {

        System.out.println();
        System.out.println("Tipo de solicitud:");

        TipoSolicitud[] tipos = TipoSolicitud.values();

        for (int i = 0; i < tipos.length; i++) {
            System.out.println(
                    (i + 1) + ". " + tipos[i]
            );
        }

        int opcion = leerEntero("Seleccione: ");

        if (opcion < 1 || opcion > tipos.length) {
            throw new RuntimeException("Tipo de solicitud inválido.");
        }

        return tipos[opcion - 1];
    }

    private static Prioridad seleccionarPrioridad() {

        System.out.println();
        System.out.println("Prioridad:");

        Prioridad[] prioridades = Prioridad.values();

        for (int i = 0; i < prioridades.length; i++) {
            System.out.println(
                    (i + 1) + ". " + prioridades[i]
            );
        }

        int opcion = leerEntero("Seleccione: ");

        if (opcion < 1 || opcion > prioridades.length) {
            throw new RuntimeException("Prioridad inválida.");
        }

        return prioridades[opcion - 1];
    }

    private static Estado seleccionarEstado() {

        System.out.println();
        System.out.println("Nuevo estado:");

        Estado[] estados = Estado.values();

        for (int i = 0; i < estados.length; i++) {
            System.out.println(
                    (i + 1) + ". " + estados[i]
            );
        }

        int opcion = leerEntero("Seleccione: ");

        if (opcion < 1 || opcion > estados.length) {
            throw new RuntimeException("Estado inválido.");
        }

        return estados[opcion - 1];
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {

        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    private static Long leerLong(String mensaje) {

        while (true) {
            try {
                return Long.parseLong(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }
}