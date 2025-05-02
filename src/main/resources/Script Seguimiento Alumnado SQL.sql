-- -----------------------------------------------------
-- SCHEMA guzpasen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS guzpasen;
CREATE DATABASE IF NOT EXISTS guzpasen;
USE guzpasen;
ALTER DATABASE guzpasen CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
SET default_storage_engine = InnoDB;

-- -----------------------------------------------------
-- TABLA ALUMNO - ANTONIO.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Alumno (
    dni BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    nombre_tutor_legal VARCHAR(30),
    apellidos_tutor_legal VARCHAR(50),
    email_tutor_legal VARCHAR(50)
);

-- -----------------------------------------------------
-- TABLA SANCION - ROBERTO V.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Sancion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    alumno_sancionado BIGINT NOT NULL,
    fecha DATE NOT NULL,
    tipo_sancion ENUM('CON_EXPULSION_DENTRO', 'CON_EXPULSION_FUERA', 'SIN_EXPULSION') NOT NULL,
    duracion VARCHAR(100),
    descripcion VARCHAR(200),
    id_parte BIGINT,
    FOREIGN KEY (alumno_sancionado) REFERENCES Alumno(dni)
);

-- -----------------------------------------------------
-- TABLA PARTE - ANTONIO.
-- -----------------------------------------------------
CREATE TABLE Parte (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    motivo VARCHAR(30),
    fecha DATE,
    hora TIME,
    descripcion VARCHAR(100),
    lugar VARCHAR (30),
    id_sancion BIGINT,
    FOREIGN KEY (id_sancion)
        REFERENCES Sancion (id)
);

-- -----------------------------------------------------
-- ACTUALIZACION TABLA ALUMNO - ANTONIO.
-- -----------------------------------------------------
ALTER TABLE Sancion
ADD FOREIGN KEY (id_parte) REFERENCES Parte(id);

-- -----------------------------------------------------
-- TABLA TAREA - SERGIO.
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Tarea (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    estado ENUM('COMPLETADA', 'PENDIENTE', 'EN_PROGRESO') NOT NULL,
    fechaCreacion DATE NOT NULL,
    fechaLimite DATE NOT NULL,
    id_sancion BIGINT NOT NULL,
    FOREIGN KEY (id_sancion) REFERENCES Sancion(id)
);

GRANT ALL PRIVILEGES ON guzpasen.* TO 'roberto'@'localhost';
FLUSH PRIVILEGES;





