# Checklist de revisión de código

## 1. Objetivo

Establecer criterios para revisar la calidad interna del código del Sistema de Gestión de Solicitudes Académicas, identificando posibles problemas de mantenibilidad, legibilidad, acoplamiento y organización.

Este checklist se utiliza como guía durante la revisión de los cambios realizados en el proceso de refactorización.

## 2. Criterios de revisión

| ID | Criterio | Aspectos a verificar |
|---|---|---|
| CR01 | Responsabilidad única | Cada clase y método tiene una responsabilidad clara y acotada. |
| CR02 | Separación de responsabilidades | La presentación, la lógica de aplicación y el acceso a datos se encuentran diferenciados. |
| CR03 | Duplicación de código | No existen bloques de código repetidos que puedan simplificarse mediante reutilización razonable. |
| CR04 | Acoplamiento | Los componentes dependen de abstracciones cuando esto aporta un beneficio concreto y evita dependencias innecesarias. |
| CR05 | Manejo de errores | Las excepciones permiten identificar las causas de error y se manejan de forma adecuada. |
| CR06 | Validaciones | Los datos de entrada y las reglas de negocio se validan de manera consistente. |
| CR07 | Legibilidad | Los nombres de clases, métodos y variables son claros y representan su propósito. |
| CR08 | Complejidad | Los métodos mantienen una estructura comprensible y evitan lógica innecesariamente compleja. |
| CR09 | Acceso a datos | Los recursos JDBC se gestionan correctamente y las consultas mantienen una estructura clara. |
| CR10 | Persistencia | Las operaciones conservan el comportamiento esperado y los datos se almacenan correctamente. |
| CR11 | Comportamiento funcional | Los cambios no alteran las funcionalidades originales del sistema. |
| CR12 | Mantenibilidad | La solución facilita futuras modificaciones sin introducir abstracciones innecesarias. |

## 3. Resultado de la revisión

Los criterios anteriores se utilizan como referencia para analizar los cambios introducidos durante la refactorización.

La verificación funcional se complementa con las pruebas de regresión documentadas en `pruebas-regresion.md`.

El checklist no implica que todos los criterios deban resolverse mediante nuevas abstracciones o modificaciones estructurales. Cada decisión debe considerar el alcance del sistema y el beneficio concreto que aporta al código.