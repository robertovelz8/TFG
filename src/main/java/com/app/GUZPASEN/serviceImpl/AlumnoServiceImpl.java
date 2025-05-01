package com.app.GUZPASEN.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.services.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Override
	public Alumno createAlumno(Alumno alumno) {
		return alumnoRepository.save(alumno);
	}

	@Override
	public Alumno getAlumnoById(String dni) {
		return alumnoRepository.findById(dni)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el alumno con dni: "+dni));
	}

	@Override
	public List<Alumno> getAllAlumnos() {
		return alumnoRepository.findAll();
	}

	public List<Alumno> getAllAlumnosOrdenadosAlfabeticamente() {
		Sort alumnosOrdenados = Sort.by(Sort.Direction.ASC, "apellidos", "nombre");
		return alumnoRepository.findAll(alumnosOrdenados);
	}
	
	@Override
	public Alumno updateAlumno(String dni, Alumno alumno) {
		Alumno alumnoEncontrado = alumnoRepository.findById(dni)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el alumno con dni: "+dni));
		
		if (alumno.getNombre() != null) alumnoEncontrado.setNombre(alumno.getNombre());
		if (alumno.getApellidos() != null) alumnoEncontrado.setApellidos(alumno.getApellidos());
		if (alumno.getEmail_tutor_legal() != null) alumnoEncontrado.setEmail_tutor_legal(alumno.getEmail_tutor_legal());
		if (alumno.getNombre_tutor_legal() != null) alumnoEncontrado.setNombre_tutor_legal(alumno.getNombre_tutor_legal());
		if (alumno.getApellidos_tutor_legal() != null) alumnoEncontrado.setApellidos_tutor_legal(alumno.getApellidos_tutor_legal());
					
		return alumnoRepository.save(alumnoEncontrado);
		
	}

	@Override
	public void deleteAlumno(String dni) {
		if (dni != null) {
			alumnoRepository.deleteById(dni);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado el alumno con dni: "+dni);
		}

	}

}
