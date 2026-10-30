# Sistema de Gestión de Solicitudes Académicas

## Descripción

Sistema de gestión de solicitudes académicas desarrollado como Trabajo Práctico Integrador de la materia Metodología de Sistemas II.

La aplicación permite administrar solicitudes académicas, estudiantes y responsables, centralizando la información y facilitando el seguimiento de las solicitudes durante su ciclo de vida.

El proyecto fue desarrollado en Java y utiliza una base de datos H2 para almacenar la información.

## Funcionalidades principales

- **Gestión de estudiantes:** registrar y consultar estudiantes.
- **Gestión de responsables:** registrar y consultar responsables.
- **Gestión de solicitudes:** crear solicitudes académicas asociadas a estudiantes.
- **Asignación de responsables:** asignar un responsable a cada solicitud.
- **Seguimiento:** modificar y consultar el estado de las solicitudes.
- **Consultas:** visualizar los registros almacenados.
- **Persistencia:** conservar la información mediante una base de datos H2.

## Tecnologías utilizadas

- **Java 21:** lenguaje de programación.
- **Maven:** gestión de dependencias y construcción del proyecto.
- **H2:** motor de base de datos relacional.
- **JDBC:** acceso y comunicación con la base de datos.
- **Git y GitHub:** control de versiones y trabajo colaborativo.

## Instalación y ejecución

### Requisitos previos

- JDK 21.
- Apache Maven (puede utilizarse el Maven integrado en IntelliJ IDEA).
- Un entorno de desarrollo compatible con Java, como IntelliJ IDEA o Eclipse.

### Pasos

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/AndresLettieri/TUP-Metodologia2.git
   ```

2. Ingresar al directorio del proyecto:

   ```bash
   cd TUP-Metodologia2/solicitudes-academicas
   ```

3. Abrir el proyecto desde el entorno de desarrollo.

4. Compilar el proyecto ejecutando las tareas `clean` y `package` desde el panel Maven, dentro de `Lifecycle`.

   También se puede ejecutar desde una terminal con Maven configurado:

   ```bash
   mvn clean package
   ```

5. Ejecutar la clase `Main`, ubicada en el paquete:

   `ar.edu.metodologia.solicitudes`

La aplicación se ejecuta mediante una interfaz de consola.

## Estructura de documentación

El repositorio incluye una carpeta `docs` con documentación relacionada con el análisis y la evolución del proyecto.

- `diagnostico.md`: relevamiento y diagnóstico inicial del código.
- `pruebas-regresion.md`: casos de prueba ejecutados luego de la refactorización.
- `evidencias/`: capturas que respaldan la ejecución de las pruebas.

## Trabajo de refactorización

Como parte del trabajo práctico se realizó un proceso de revisión y refactorización del código existente, orientado a mejorar su organización, legibilidad, mantenibilidad y separación de responsabilidades.

Los cambios se desarrollaron de manera incremental, utilizando ramas y Pull Requests para integrar las modificaciones en la rama de trabajo `refactor/v2`.

También se realizaron pruebas de regresión para verificar que las funcionalidades originales continuaran funcionando correctamente.