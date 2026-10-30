
package ar.edu.metodologia.solicitudes;

import ar.edu.metodologia.solicitudes.model.Estado;
import ar.edu.metodologia.solicitudes.model.Estudiante;
import ar.edu.metodologia.solicitudes.model.Prioridad;
import ar.edu.metodologia.solicitudes.model.TipoSolicitud;
import ar.edu.metodologia.solicitudes.model.Responsable;
import ar.edu.metodologia.solicitudes.model.Solicitud;

import java.util.List;
import java.util.Scanner;

public class Consola {

    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
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

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    public Long leerLong(String mensaje) {
        while (true) {
            try {
                return Long.parseLong(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número válido.");
            }
        }
    }

    private <T extends Enum<T>> T seleccionarOpcion(String titulo, T[] opciones) {
        mostrarOpciones(titulo, opciones);
        while (true) {
            int opcion = leerEntero("Seleccione: ");
            if (opcion >= 1 && opcion <= opciones.length)
                return opciones[opcion - 1];

            System.out.println("Opción inválida. Ingrese un número entre 1 y "+ opciones.length + ".");
        }
    }

    public TipoSolicitud seleccionarTipoSolicitud() {
        return seleccionarOpcion("Tipo de solicitud:", TipoSolicitud.values());
    }

    public Prioridad seleccionarPrioridad() {
        return seleccionarOpcion("Prioridad:", Prioridad.values());
    }

    public Estado seleccionarEstado() {
        return seleccionarOpcion("Nuevo estado:", Estado.values());
    }
    private void mostrarOpciones(String titulo, Enum<?>[] opciones) {
        System.out.println();
        System.out.println(titulo);

        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i + 1) + ". " + opciones[i]);
        }
    }

    public void mostrarEstudiantes(List<Estudiante> estudiantes) {
        System.out.println();
        System.out.println("--- Estudiantes ---");

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

    public void mostrarResponsables(List<Responsable> responsables) {
        System.out.println();
        System.out.println("--- Responsables ---");

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

    public void mostrarSolicitudes(List<Solicitud> solicitudes) {
        System.out.println();
        System.out.println("--- Solicitudes ---");

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
