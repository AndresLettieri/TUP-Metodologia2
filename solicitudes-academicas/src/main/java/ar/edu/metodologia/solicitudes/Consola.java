
package ar.edu.metodologia.solicitudes;

import ar.edu.metodologia.solicitudes.model.Estado;
import ar.edu.metodologia.solicitudes.model.Prioridad;
import ar.edu.metodologia.solicitudes.model.TipoSolicitud;

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
}
