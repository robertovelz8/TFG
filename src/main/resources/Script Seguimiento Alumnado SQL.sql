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
    lugar VARCHAR(255),
    sancion_id BIGINT
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

ALTER TABLE parte ADD FOREIGN KEY (sancion_id) REFERENCES sancion(id);

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


INSERT INTO alumno (dni, nombre, apellidos, nombre_tutor_legal, apellidos_tutor_legal, email_tutor_legal) VALUES
('12345678A', 'Juan', 'Pérez', 'María', 'López', 'maria.lopez@mail.com'),
('23456789B', 'Ana', 'García', 'José', 'Martín', 'jose.martin@mail.com'),
('34567890C', 'Luis', 'Martínez', 'Laura', 'Sánchez', 'laura.sanchez@mail.com'),
('45678901D', 'Marta', 'Rodríguez', 'Carlos', 'Fernández', 'carlos.fernandez@mail.com'),
('56789012E', 'Pedro', 'López', 'Elena', 'Gómez', 'elena.gomez@mail.com'),
('67890123F', 'Sofía', 'Hernández', 'Javier', 'Ruiz', 'javier.ruiz@mail.com'),
('78901234G', 'David', 'Sosa', 'Ana', 'Vargas', 'ana.vargas@mail.com'),
('89012345H', 'Laura', 'Jiménez', 'Miguel', 'Morales', 'miguel.morales@mail.com'),
('90123456I', 'Carlos', 'Díaz', 'Isabel', 'Navarro', 'isabel.navarro@mail.com'),
('01234567J', 'Elena', 'Cruz', 'Raúl', 'Torres', 'raul.torres@mail.com'),
('11234567K', 'Javier', 'Castillo', 'Carmen', 'Ortiz', 'carmen.ortiz@mail.com'),
('12234567L', 'Isabel', 'Vega', 'Sergio', 'Molina', 'sergio.molina@mail.com'),
('13234567M', 'Miguel', 'Ramos', 'Lucía', 'García', 'lucia.garcia@mail.com'),
('14234567N', 'Carmen', 'Santos', 'Diego', 'Pérez', 'diego.perez@mail.com'),
('15234567O', 'Sergio', 'Flores', 'Nuria', 'Gil', 'nuria.gil@mail.com'),
('16234567P', 'Lucía', 'Herrera', 'Alberto', 'Navarro', 'alberto.navarro@mail.com'),
('17234567Q', 'Diego', 'Moreno', 'Isabel', 'Díaz', 'isabel.diaz@mail.com'),
('18234567R', 'Nuria', 'Molina', 'Raúl', 'Herrera', 'raul.herrera@mail.com'),
('19234567S', 'Alberto', 'Ruiz', 'Laura', 'Gómez', 'laura.gomez@mail.com'),
('20234567T', 'Carmen', 'Ortiz', 'Sergio', 'Ramírez', 'sergio.ramirez@mail.com');

INSERT INTO sancion (fecha, tipo_sancion, duracion, alumno) VALUES
('2024-01-10', 'CON_EXPULSION_DENTRO', '3 días', '12345678A'),
('2024-01-15', 'SIN_EXPULSION', 'N/A', '23456789B'),
('2024-02-05', 'CON_EXPULSION_FUERA', '1 semana', '34567890C'),
('2024-02-20', 'SIN_EXPULSION', 'N/A', '45678901D'),
('2024-03-01', 'CON_EXPULSION_DENTRO', '2 días', '56789012E'),
('2024-03-10', 'CON_EXPULSION_FUERA', '5 días', '67890123F'),
('2024-03-15', 'SIN_EXPULSION', 'N/A', '78901234G'),
('2024-04-01', 'CON_EXPULSION_DENTRO', '4 días', '89012345H'),
('2024-04-10', 'SIN_EXPULSION', 'N/A', '90123456I'),
('2024-04-15', 'CON_EXPULSION_FUERA', '3 días', '01234567J'),
('2024-05-01', 'SIN_EXPULSION', 'N/A', '11234567K'),
('2024-05-10', 'CON_EXPULSION_DENTRO', '1 día', '12234567L'),
('2024-05-15', 'CON_EXPULSION_FUERA', '2 semanas', '13234567M'),
('2024-06-01', 'SIN_EXPULSION', 'N/A', '14234567N'),
('2024-06-05', 'CON_EXPULSION_DENTRO', '6 días', '15234567O'),
('2024-06-10', 'SIN_EXPULSION', 'N/A', '16234567P'),
('2024-06-15', 'CON_EXPULSION_FUERA', '3 semanas', '17234567Q'),
('2024-07-01', 'SIN_EXPULSION', 'N/A', '18234567R'),
('2024-07-05', 'CON_EXPULSION_DENTRO', '7 días', '19234567S'),
('2024-07-10', 'SIN_EXPULSION', 'N/A', '20234567T');

INSERT INTO parte (motivo, fecha, hora, descripcion, lugar, sancion_id) VALUES
('Falta grave', '2024-01-10', '08:30:00', 'Alumno causó disturbios en clase.', 'Aula 101', 1),
('Retraso reiterado', '2024-01-15', '09:00:00', 'Alumno llegó tarde varias veces.', 'Entrada principal', 2),
('Agresión verbal', '2024-02-05', '11:15:00', 'Alumno insultó a un profesor.', 'Patio', 3),
('Uso indebido de móvil', '2024-02-20', '10:45:00', 'Alumno usando móvil en clase.', 'Aula 203', 4),
('Desobediencia', '2024-03-01', '14:00:00', 'Alumno no siguió instrucciones.', 'Biblioteca', 5),
('Pelea', '2024-03-10', '15:30:00', 'Alumno involucrado en pelea.', 'Patio', 6),
('Falta de respeto', '2024-03-15', '08:00:00', 'Alumno habló mal a compañeros.', 'Pasillo', 7),
('Daños materiales', '2024-04-01', '09:30:00', 'Alumno rompió una ventana.', 'Aula 102', 8),
('Uso de sustancias prohibidas', '2024-04-10', '12:00:00', 'Alumno con sustancias ilícitas.', 'Baños', 9),
('Falta leve', '2024-04-15', '13:45:00', 'Alumno no entregó tarea.', 'Aula 104', 10),
('Comportamiento disruptivo', '2024-05-01', '08:30:00', 'Alumno interrumpió clase.', 'Aula 105', 11),
('Inasistencia injustificada', '2024-05-10', '09:00:00', 'Alumno faltó sin aviso.', 'Entrada principal', 12),
('Falta grave', '2024-05-15', '11:00:00', 'Alumno maltrató a un compañero.', 'Patio', 13),
('Uso indebido de recursos', '2024-06-01', '10:00:00', 'Alumno usó ordenador sin permiso.', 'Sala informática', 14),
('Comportamiento agresivo', '2024-06-05', '14:15:00', 'Alumno amenazó a otro.', 'Pasillo', 15),
('Desobediencia reiterada', '2024-06-10', '08:45:00', 'Alumno no cumplió normas.', 'Aula 106', 16),
('Falta leve', '2024-06-15', '09:30:00', 'Alumno no respetó normas.', 'Patio', 17),
('Ausencia injustificada', '2024-07-01', '10:30:00', 'Alumno faltó sin permiso.', 'Entrada principal', 18),
('Falta grave', '2024-07-05', '13:00:00', 'Alumno causó daño grave.', 'Aula 107', 19),
('Uso indebido del móvil', '2024-07-10', '14:30:00', 'Alumno usó móvil en clase.', 'Aula 108', 20);

INSERT INTO tarea (titulo, descripcion, estado, fecha_creacion, fecha_limite, sancion) VALUES
('Informe de incidente', 'Redactar informe completo del incidente.', 'PENDIENTE', '2024-01-10', '2024-01-20', 1),
('Revisión de normas', 'Estudiar el reglamento interno.', 'EN_PROGRESO', '2024-01-15', '2024-01-25', 2),
('Entrevista con tutor', 'Reunión con tutor legal.', 'COMPLETADA', '2024-02-05', '2024-02-15', 3),
('Sesión de mediación', 'Mediar conflicto entre alumnos.', 'PENDIENTE', '2024-02-20', '2024-03-01', 4),
('Trabajo comunitario', 'Realizar actividades para reparar daño.', 'EN_PROGRESO', '2024-03-01', '2024-03-10', 5),
('Curso de comportamiento', 'Participar en curso de conducta.', 'PENDIENTE', '2024-03-10', '2024-03-20', 6),
('Carta de disculpa', 'Escribir carta formal de disculpa.', 'COMPLETADA', '2024-03-15', '2024-03-25', 7),
('Reparación de daños', 'Arreglar los daños materiales causados.', 'EN_PROGRESO', '2024-04-01', '2024-04-10', 8),
('Asistencia a charla', 'Asistir a charla sobre drogas.', 'PENDIENTE', '2024-04-10', '2024-04-20', 9),
('Entrega de tarea pendiente', 'Realizar y entregar tarea.', 'COMPLETADA', '2024-04-15', '2024-04-25', 10),
('Participar en taller', 'Taller de comportamiento en grupo.', 'PENDIENTE', '2024-05-01', '2024-05-10', 11),
('Informe de asistencia', 'Justificar ausencias.', 'EN_PROGRESO', '2024-05-10', '2024-05-20', 12),
('Carta a padres', 'Enviar carta explicativa a padres.', 'COMPLETADA', '2024-05-15', '2024-05-25', 13),
('Control de acceso', 'Monitorear uso de recursos.', 'PENDIENTE', '2024-06-01', '2024-06-10', 14),
('Reunión disciplinaria', 'Asistir a reunión con dirección.', 'EN_PROGRESO', '2024-06-05', '2024-06-15', 15),
('Entrega de plan de mejora', 'Presentar plan de mejora.', 'PENDIENTE', '2024-06-10', '2024-06-20', 16),
('Reporte de comportamiento', 'Informe semanal de conducta.', 'COMPLETADA', '2024-06-15', '2024-06-25', 17),
('Asistencia a tutoría', 'Participar en tutoría.', 'PENDIENTE', '2024-07-01', '2024-07-10', 18),
('Informe final', 'Elaborar informe final de sanción.', 'EN_PROGRESO', '2024-07-05', '2024-07-15', 19),
('Reflexión escrita', 'Redactar reflexión personal.', 'PENDIENTE', '2024-07-10', '2024-07-20', 20);


CREATE USER IF NOT EXISTS 'roberto'@'localhost' IDENTIFIED BY 'dam1';
GRANT ALL PRIVILEGES ON *.* TO 'roberto'@'localhost';
FLUSH PRIVILEGES;