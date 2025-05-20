-- -----------------------------------------------------
-- SCHEMA guzpasen
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS guzpasen;
CREATE DATABASE IF NOT EXISTS guzpasen;
USE guzpasen;
ALTER DATABASE guzpasen CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
SET default_storage_engine = InnoDB;

CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    clave VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR', 'PROFESOR', 'GESTOR_INCIDENCIAS', 'TECNICO') NOT NULL,
    usuario_movil BOOLEAN DEFAULT FALSE
);

-- Tabla Alumno
CREATE TABLE IF NOT EXISTS alumno (
    dni VARCHAR(255) PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellidos VARCHAR(255) NOT NULL,
    nombre_tutor_legal VARCHAR(255),
    apellidos_tutor_legal VARCHAR(255),
    email_tutor_legal VARCHAR(255)
);

-- Tabla Parte
CREATE TABLE IF NOT EXISTS parte (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    motivo VARCHAR(255) NOT NULL,
    fecha DATE,
    hora TIME,
    descripcion TEXT,
    lugar VARCHAR(255)
);

-- Tabla Sancion
CREATE TABLE IF NOT EXISTS sancion (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE,
    tipo_sancion ENUM('CON_EXPULSION_DENTRO', 'CON_EXPULSION_FUERA', 'SIN_EXPULSION'),
    duracion VARCHAR(255),
    alumno VARCHAR(255),
    CONSTRAINT fk_sancion_alumno FOREIGN KEY (alumno) REFERENCES alumno(dni)
);

-- Tabla Tarea
CREATE TABLE IF NOT EXISTS tarea (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    estado ENUM('COMPLETADA', 'EN_PROGRESO', 'PENDIENTE'),
    fecha_creacion DATE,
    fecha_limite DATE,
    sancion BIGINT,
    CONSTRAINT fk_tarea_sancion FOREIGN KEY (sancion) REFERENCES sancion(id)
);


CREATE USER IF NOT EXISTS 'roberto'@'localhost' IDENTIFIED BY 'dam1';
GRANT ALL PRIVILEGES ON *.* TO 'roberto'@'localhost';
FLUSH PRIVILEGES;
