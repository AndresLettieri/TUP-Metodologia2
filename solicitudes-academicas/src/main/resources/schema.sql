CREATE TABLE estudiante (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL,
                            email VARCHAR(150) NOT NULL,
                            documento VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE responsable (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(100) NOT NULL,
                             area VARCHAR(100) NOT NULL,
                             email VARCHAR(150) NOT NULL
);

CREATE TABLE solicitud (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           estudiante_id BIGINT NOT NULL,
                           tipo VARCHAR(30) NOT NULL,
                           descripcion VARCHAR(1000) NOT NULL,
                           prioridad VARCHAR(20) NOT NULL,
                           estado VARCHAR(20) NOT NULL,
                           responsable_id BIGINT,
                           fecha_creacion TIMESTAMP NOT NULL,

                           CONSTRAINT fk_solicitud_estudiante
                               FOREIGN KEY (estudiante_id)
                                   REFERENCES estudiante(id),

                           CONSTRAINT fk_solicitud_responsable
                               FOREIGN KEY (responsable_id)
                                   REFERENCES responsable(id)
);