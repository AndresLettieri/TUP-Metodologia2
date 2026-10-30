# Decisiones técnicas de la refactorización

## 1. Objetivo

Documentar las principales decisiones técnicas adoptadas durante el proceso de refactorización del Sistema de Gestión de Solicitudes Académicas, incluyendo las soluciones elegidas y los motivos que justifican su implementación.

Las decisiones buscan mejorar la organización, legibilidad y mantenibilidad del código, conservando las funcionalidades originales del sistema.

## 2. Decisiones adoptadas

### DT01. Separación de la interacción por consola

**Problema identificado:** La clase `Main` concentraba la presentación de menús, lectura de datos y visualización de resultados, además de coordinar las operaciones de la aplicación.

**Decisión:** Crear la clase `Consola` para centralizar las tareas relacionadas con la interacción por consola.

**Justificación:** Permite reducir las responsabilidades de `Main` y separar la presentación de la lógica de coordinación de la aplicación.

### DT02. Reutilización de la selección de enumeraciones

**Problema identificado:** Existían métodos con lógica repetida para mostrar y seleccionar valores de distintas enumeraciones.

**Decisión:** Implementar un método genérico en `Consola` que permite seleccionar opciones de diferentes enumeraciones.

**Justificación:** Reduce la duplicación de código y permite mantener un único mecanismo de lectura y validación de opciones.

### DT03. Separación de la presentación de listados

**Problema identificado:** `Main` contenía lógica para recorrer y mostrar los registros obtenidos desde los servicios.

**Decisión:** Incorporar en `Consola` métodos específicos para presentar los listados de estudiantes, responsables y solicitudes.

**Justificación:** Centraliza la presentación de resultados y mantiene a `Main` enfocado en coordinar el flujo de la aplicación.

### DT04. Desacoplamiento de `SolicitudService`

**Problema identificado:** `SolicitudService` creaba directamente sus dependencias concretas de acceso a datos.

**Decisión:** Modificar la construcción del servicio para recibir sus dependencias mediante el constructor.

**Justificación:** Reduce el acoplamiento con las implementaciones concretas y facilita la sustitución de dependencias y las pruebas aisladas.

### DT05. Mejora del manejo de errores

**Problema identificado:** El uso de excepciones genéricas dificultaba distinguir las diferentes causas de error.

**Decisión:** Revisar el manejo de errores e incorporar un tratamiento más específico de las situaciones identificadas durante las operaciones.

**Justificación:** Facilita la identificación de problemas y permite ofrecer respuestas más claras ante errores de validación y acceso a datos.

### DT06. Reducción de duplicación en JDBC

**Problema identificado:** Los repositories repetían estructuras similares para ejecutar operaciones sobre la base de datos.

**Decisión:** Reutilizar las partes comunes del acceso JDBC, manteniendo en cada repository las particularidades de sus consultas y operaciones.

**Justificación:** Reduce la repetición y facilita el mantenimiento del acceso a datos sin ocultar la lógica específica de cada repository.

## 3. Decisiones de diseño que se mantienen

Durante la refactorización se decidió conservar determinadas características de la arquitectura original:

- **Java:** se mantiene como lenguaje de desarrollo.
- **Maven:** continúa utilizándose para administrar dependencias y construir el proyecto.
- **H2:** se conserva como motor de base de datos.
- **JDBC:** se mantiene como mecanismo de acceso a datos.
- **Interfaz por consola:** se conserva por ser suficiente para el alcance del trabajo práctico.
- **Arquitectura por capas:** se mantiene la separación entre presentación, servicios y repositories.

Estas características se consideran decisiones adecuadas para el alcance y la complejidad del sistema, por lo que no se justificó reemplazarlas durante esta etapa.

## 4. Consideraciones finales

Las decisiones adoptadas se implementaron de manera incremental, utilizando ramas y Pull Requests para integrar los cambios.

Cada modificación buscó resolver problemas identificados durante el diagnóstico inicial, evitando incorporar complejidad innecesaria o alterar el comportamiento funcional esperado.

La validación de los cambios se realizó mediante pruebas de regresión, cuyos casos y resultados se encuentran documentados en `pruebas-regresion.md`.