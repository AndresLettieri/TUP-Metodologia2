# Diagnóstico técnico de la versión inicial

## 1. Objetivo

El objetivo de este documento es registrar los principales problemas de calidad interna identificados en la versión inicial del Sistema de Gestión de Solicitudes Académicas.

El análisis se centra en aspectos relacionados con la mantenibilidad, la duplicación de código, la distribución de responsabilidades, el acoplamiento entre componentes y el manejo de errores.

La versión inicial fue ejecutada y probada antes de iniciar las tareas de refactorización. El propósito de esta etapa no es modificar su comportamiento, sino identificar oportunidades de mejora que permitan construir una segunda versión con una estructura interna más clara y mantenible.

## 2. Alcance del diagnóstico

Se revisaron las clases principales de la aplicación, considerando los siguientes componentes:

- `Main`: interacción con el usuario y coordinación de las operaciones.
- `EstudianteService`, `ResponsableService` y `SolicitudService`: lógica de aplicación y validaciones.
- `EstudianteRepository`, `ResponsableRepository` y `SolicitudRepository`: acceso a la base de datos.
- `DatabaseConnection`: configuración de la conexión a la base de datos.

El análisis considera el alcance de la aplicación y las decisiones de diseño adoptadas para este prototipo. No se consideran problemas el uso de Java, Maven, JDBC, H2, una interfaz por consola ni la organización del proyecto en paquetes, ya que son decisiones compatibles con los objetivos y la complejidad del sistema.

## 3. Problemas identificados

Se identificaron nueve oportunidades de mejora en el código de la versión inicial.

| ID | Problema identificado | Ubicación principal | Impacto | Prioridad |
|---|---|---|---|---|
| D01 | Concentración de responsabilidades | `Main` | Dificulta el mantenimiento y la modificación de la interfaz por consola. | Alta |
| D02 | Validaciones duplicadas | `EstudianteService`, `ResponsableService` | Aumenta la duplicación y el costo de mantener reglas consistentes. | Media |
| D03 | Código repetido para seleccionar enumeraciones | `Main` | Genera repetición en la lectura y validación de opciones. | Media |
| D04 | Estructuras de acceso a datos repetidas | Repositories | Incrementa el esfuerzo necesario para mantener y corregir el acceso a la base de datos. | Alta |
| D05 | Dependencias concretas creadas directamente por el servicio | `SolicitudService` | Aumenta el acoplamiento y dificulta sustituir dependencias o realizar pruebas aisladas. | Alta |
| D06 | Uso de excepciones genéricas | Services y manejo de errores | Dificulta distinguir las causas de error y responder de manera específica. | Alta |
| D07 | Configuración de base de datos fija en el código | `DatabaseConnection` | Complica la adaptación de la aplicación a distintos entornos. | Media |
| D08 | Uso de `null` para representar entidades inexistentes | Repositories y services | Obliga a los consumidores a verificar valores nulos y puede favorecer errores si se omiten controles. | Media |
| D09 | Métodos que solamente delegan llamadas | `SolicitudService` | Introducen niveles de indirección sin aportar lógica adicional. | Baja |

Las prioridades representan una estimación del impacto sobre la mantenibilidad y la evolución del sistema, considerando el alcance del proyecto.

## 4. Análisis de los principales problemas

### D01. Concentración de responsabilidades en `Main`

La clase `Main` concentra distintas tareas: inicialización de la aplicación, presentación del menú, lectura de datos, selección de opciones, coordinación de operaciones y presentación de resultados.

Esta concentración dificulta modificar la interacción con el usuario sin afectar otras partes del flujo de la aplicación. Se relaciona con el principio de responsabilidad única (SRP).

**Mejora propuesta:** separar las responsabilidades de interacción por consola de la coordinación general de la aplicación, manteniendo el comportamiento funcional existente.

### D02. Validaciones duplicadas

En `EstudianteService` y `ResponsableService` se repiten validaciones similares, como la comprobación de valores nulos, cadenas vacías y espacios en blanco para campos obligatorios.

La duplicación aumenta el riesgo de que futuras modificaciones se implementen de manera diferente en cada servicio.

**Mejora propuesta:** extraer las validaciones comunes en métodos reutilizables, siempre que esto simplifique el código y evite crear abstracciones innecesarias.

### D03. Repetición en la selección de enumeraciones

En `Main` existen métodos que implementan una lógica similar para permitir la selección de valores de enumeraciones, como tipo de solicitud, prioridad y estado.

Aunque cada enumeración tiene sus propios valores, el procedimiento de mostrar opciones, leer la selección y validar el valor presenta similitudes.

**Mejora propuesta:** evaluar una implementación reutilizable para reducir la repetición sin perder claridad en las opciones que se presentan al usuario.

### D04. Repetición en los repositories

Los repositories utilizan estructuras JDBC similares para obtener conexiones, preparar sentencias, ejecutar consultas, recorrer resultados y gestionar excepciones.

Esta repetición hace que determinadas modificaciones deban realizarse en varias clases y dificulta mantener un criterio uniforme para el acceso a datos.

**Mejora propuesta:** identificar las partes de código que realmente pueden reutilizarse y reducir la duplicación sin ocultar las particularidades de cada consulta.

### D05. Acoplamiento de `SolicitudService` con implementaciones concretas

El constructor de `SolicitudService` instancia directamente `SolicitudRepository`, `EstudianteRepository` y `ResponsableRepository`.

De esta forma, el servicio decide qué implementaciones concretas utiliza, en lugar de recibir sus dependencias desde el exterior. Esto dificulta reemplazarlas y realizar pruebas aisladas.

**Mejora propuesta:** incorporar inyección de dependencias mediante el constructor y evaluar el uso de abstracciones donde aporten un beneficio concreto. Esta mejora se relaciona con el principio de inversión de dependencias (DIP).

### D06. Uso de excepciones genéricas

El uso de `RuntimeException` para representar diferentes situaciones dificulta identificar si el error se debe a una validación de negocio, una entidad inexistente o un problema de acceso a datos.

Esto también limita la posibilidad de presentar mensajes específicos y de diferenciar los errores esperados de los problemas técnicos.

**Mejora propuesta:** definir una estrategia de manejo de errores más clara, utilizando excepciones específicas cuando corresponda y conservando la información original de los errores técnicos.

### D07. Configuración fija de la conexión

La URL, el usuario y la contraseña de la conexión están definidos directamente como constantes en `DatabaseConnection`.

Si fuera necesario modificar la ubicación de la base de datos o utilizar otra configuración, sería necesario intervenir en el código fuente.

**Mejora propuesta:** separar la configuración de la conexión de la lógica de acceso a datos, manteniendo una configuración predeterminada adecuada para el prototipo.

### D08. Uso de `null` para representar entidades inexistentes

Algunas operaciones de búsqueda devuelven `null` cuando no encuentran una entidad. Esto obliga a los servicios que utilizan esos métodos a verificar explícitamente el resultado.

Si una comprobación se omite, podrían producirse errores durante la ejecución.

**Mejora propuesta:** revisar cómo se representan los resultados de búsqueda y mantener un tratamiento explícito de las entidades inexistentes. La alternativa elegida deberá ser coherente con el diseño general y no agregar complejidad innecesaria.

### D09. Métodos que solamente delegan llamadas

En `SolicitudService` existen métodos auxiliares que únicamente invocan a otros métodos del repository, sin agregar validaciones, transformaciones ni lógica adicional.

Esta delegación no aporta un beneficio claro y agrega un nivel de indirección al flujo de ejecución.

**Mejora propuesta:** eliminar las delegaciones innecesarias y realizar las llamadas directamente desde el servicio cuando no se justifique mantener un método intermedio.

## 5. Criterios para la refactorización

A partir de los problemas identificados, la evolución hacia la segunda versión seguirá estos criterios:

1. Mantener las funcionalidades y reglas de negocio de la versión inicial.
2. Aplicar los cambios de manera incremental, evitando modificar varias responsabilidades simultáneamente.
3. Priorizar los problemas de mayor impacto sobre la mantenibilidad y el acoplamiento.
4. Realizar pruebas después de cada cambio relevante para detectar posibles regresiones.
5. Evitar introducir abstracciones o estructuras que no aporten un beneficio concreto al sistema.
6. Registrar los cambios mediante commits claros y mantener evidencia de las verificaciones realizadas.

## 6. Conclusión

El diagnóstico indica que la versión inicial cumple con las funcionalidades previstas, pero presenta oportunidades de mejora en su calidad interna. Los principales problemas se relacionan con la concentración de responsabilidades, la repetición de código, el acoplamiento entre servicios y repositories, y el manejo de errores.

Estos hallazgos constituyen la base para planificar la refactorización de manera controlada. Las modificaciones se realizarán progresivamente y se verificarán mediante pruebas, buscando mejorar la mantenibilidad sin alterar el comportamiento esperado del sistema.