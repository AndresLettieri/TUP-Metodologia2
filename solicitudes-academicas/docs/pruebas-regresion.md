# Pruebas de regresión

## 1. Objetivo

Verificar que las funcionalidades principales del Sistema de Gestión de Solicitudes Académicas continúen funcionando correctamente luego de los cambios realizados durante la etapa de refactorización.

Las pruebas se realizaron manualmente, utilizando la aplicación desde la consola y comprobando tanto los resultados esperados como el comportamiento ante situaciones inválidas o excepcionales.

## 2. Alcance

Se probaron las siguientes funcionalidades:

- Registro de estudiantes.
- Registro de responsables.
- Creación de solicitudes académicas.
- Asignación de responsables.
- Modificación del estado de las solicitudes.
- Consulta y listado de registros.
- Validación de entradas del usuario.
- Manejo de registros inexistentes.
- Persistencia de la información.

## 3. Casos de prueba

| ID | Prueba | Resultado esperado | Estado | Evidencia                                             |
|---|---|---|---|-------------------------------------------------------|
| PR-01 | Registrar un estudiante | El estudiante se registra correctamente. | Aprobada | [Captura](evidencias/PR-01-registro-estudiante.png)   |
| PR-02 | Registrar un responsable | El responsable se registra correctamente. | Aprobada | [Captura](evidencias/PR-02-registro-responsable.png)  |
| PR-03 | Registrar una solicitud | La solicitud se crea correctamente. | Aprobada | [Captura](evidencias/PR-03-creacion-solicitud.png)    |
| PR-04 | Asignar un responsable | La solicitud queda asociada al responsable. | Aprobada | [Captura](evidencias/PR-04-asignacion-responsable.png) |
| PR-05 | Cambiar el estado | El estado se actualiza correctamente. | Aprobada | [Captura](evidencias/PR-05-cambio-estado.png)         |
| PR-06 | Consultar los listados | Se muestran los registros correctamente. | Aprobada | [Captura](evidencias/PR-06-listados.png)              |
| PR-07 | Consultar listados vacíos | Se informa que no existen registros. | Aprobada | [Captura](evidencias/PR-07-validacion-vacio.png)      |
| PR-08 | Ingresar una opción inválida | El sistema informa el error y permite reintentar. | Aprobada | [Captura](evidencias/PR-08-validacion-menu.png)       |
| PR-09 | Ingresar texto donde se espera un número | El sistema controla la entrada inválida. | Aprobada | [Captura](evidencias/PR-09-validacion-numerica.png)   |
| PR-10 | Consultar registros inexistentes | El sistema informa que el registro no existe. | Aprobada | [Captura](evidencias/PR-10-registro-inexistente.png)  |
| PR-11 | Reiniciar la aplicación | Los datos registrados se mantienen disponibles. | Aprobada | [Captura](evidencias/PR-11-persistencia.png)          |
## 4. Resultado general

Se ejecutaron los 11 casos de prueba definidos para verificar las funcionalidades principales del sistema.

Los resultados obtenidos fueron satisfactorios: todas las pruebas finalizaron correctamente y no se identificaron fallas funcionales que impidieran el uso de la aplicación.

## 5. Conclusión

Las pruebas permitieron comprobar que los cambios introducidos durante la refactorización no afectaron el comportamiento esperado del sistema.

Se verificaron tanto los flujos habituales de operación como algunas situaciones de entrada inválida y consulta de información inexistente. También se comprobó la persistencia de los datos luego de reiniciar la aplicación.

Con estos resultados, se considera completada la etapa de pruebas de regresión.