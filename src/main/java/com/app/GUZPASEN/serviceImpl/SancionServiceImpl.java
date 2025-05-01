package com.app.GUZPASEN.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.repositories.ParteRepository;
import com.app.GUZPASEN.repositories.SancionRepository;
import com.app.GUZPASEN.services.SancionService;

@Service
public class SancionServiceImpl implements SancionService {

	@Autowired
	private SancionRepository sancionRepository;

	@Autowired
	private ParteRepository parteRepository;

	@Autowired
	private AlumnoRepository alumnoRepository;
	

	@Override
	public Sancion createSancion(Sancion sancion) {
		
		Parte parteEncontrado = parteRepository.findById(sancion.getParte().getId()).orElseThrow(
				() -> new ResourceNotFoundException("No se ha encontrado el parte con id: " + sancion.getParte().getId()));

		Alumno alumnoEncontrado = alumnoRepository.findById(sancion.getAlumno().getDni())
				.orElseThrow(() -> new ResourceNotFoundException(
						"No se ha encontrado el alumno con id: " + sancion.getAlumno().getDni()));

		if (sancion.getFecha() == null) {
			sancion.setFecha(LocalDate.now());
		}
		
		sancion.setParte(parteEncontrado);
		sancion.setAlumno(alumnoEncontrado);
		

		return sancionRepository.save(sancion);
	}

	@Override
	public Sancion getSancionById(Long id) {
		return sancionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: " + id));
	}

	@Override
	public List<Sancion> getAllSanciones() {
		return sancionRepository.findAll();
	}

	@Override
	public Sancion updateSancion(Long id, Sancion sancion) {
		Sancion sancionEncontrada = sancionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: " + id));
		
		if (sancion.getFecha() == null) {
			sancion.setFecha(LocalDate.now());
		}

		if (sancion.getDuracion() != null)
			sancionEncontrada.setDuracion(sancion.getDuracion());
		if (sancion.getFecha() != null)
			sancionEncontrada.setFecha(sancion.getFecha());
		if (sancion.getTipoSancion() != null)
			sancionEncontrada.setTipoSancion(sancion.getTipoSancion());

		return sancionRepository.save(sancionEncontrada);
	}

	@Override
	public void deleteSancion(Long id) {

		if (id != null) {
			sancionRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado la sanción con id: " + id);
		}

	}

}
