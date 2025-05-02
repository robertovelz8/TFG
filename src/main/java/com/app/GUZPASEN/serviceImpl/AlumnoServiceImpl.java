package com.app.GUZPASEN.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.services.AlumnoService;

/**
 * Implementación del servicio {@link AlumnoService} para gestionar los datos de los alumnos.
 * Permite realizar operaciones de creación, obtención, actualización y eliminación de alumnos.
 * También incluye funcionalidad para obtener alumnos ordenados alfabéticamente.
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    /**
     * Crea un nuevo alumno en el sistema.
     *
     * @param alumno Objeto Alumno que se desea registrar.
     * @return El alumno creado.
     */
    @Override
    public Alumno createAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    /**
     * Obtiene un alumno por su DNI.
     *
     * @param dni DNI del alumno a buscar.
     * @return El alumno encontrado.
     * @throws ResourceNotFoundException si no se encuentra el alumno.
     */
    @Override
    public Alumno getAlumnoById(String dni) {
        return alumnoRepository.findById(dni)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el alumno con dni: " + dni));
    }

    /**
     * Devuelve una lista con todos los alumnos registrados.
     *
     * @return Lista de alumnos.
     */
    @Override
    public List<Alumno> getAllAlumnos() {
        return alumnoRepository.findAll();
    }

    /**
     * Devuelve una lista de alumnos ordenados por apellidos y nombre.
     *
     * @return Lista de alumnos ordenada alfabéticamente.
     */
    public List<Alumno> getAllAlumnosOrdenadosAlfabeticamente() {
        Sort alumnosOrdenados = Sort.by(Sort.Direction.ASC, "apellidos", "nombre");
        return alumnoRepository.findAll(alumnosOrdenados);
    }

    /**
     * Actualiza los datos de un alumno existente.
     *
     * @param dni    DNI del alumno a actualizar.
     * @param alumno Datos nuevos del alumno.
     * @return El alumno actualizado.
     * @throws ResourceNotFoundException si no se encuentra el alumno.
     */
    @Override
    public Alumno updateAlumno(String dni, Alumno alumno) {
        Alumno alumnoEncontrado = alumnoRepository.findById(dni)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el alumno con dni: " + dni));

        if (alumno.getNombre() != null) alumnoEncontrado.setNombre(alumno.getNombre());
        if (alumno.getApellidos() != null) alumnoEncontrado.setApellidos(alumno.getApellidos());
        if (alumno.getEmail_tutor_legal() != null) alumnoEncontrado.setEmail_tutor_legal(alumno.getEmail_tutor_legal());
        if (alumno.getNombre_tutor_legal() != null) alumnoEncontrado.setNombre_tutor_legal(alumno.getNombre_tutor_legal());
        if (alumno.getApellidos_tutor_legal() != null) alumnoEncontrado.setApellidos_tutor_legal(alumno.getApellidos_tutor_legal());

        return alumnoRepository.save(alumnoEncontrado);
    }

    /**
     * Elimina un alumno del sistema según su DNI.
     *
     * @param dni DNI del alumno a eliminar.
     * @throws ResourceNotFoundException si el DNI es nulo.
     */
    @Override
    public void deleteAlumno(String dni) {
        if (dni != null) {
            alumnoRepository.deleteById(dni);
        } else {
            throw new ResourceNotFoundException("No se ha encontrado el alumno con dni: " + dni);
        }
    }
}
