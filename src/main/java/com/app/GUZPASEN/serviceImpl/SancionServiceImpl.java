package com.app.GUZPASEN.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.controllers.NotificacionController;
import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.repositories.ParteRepository;
import com.app.GUZPASEN.repositories.SancionRepository;
import com.app.GUZPASEN.services.SancionService;

/**
 * Implementación del servicio {@link SancionService} para gestionar sanciones aplicadas a alumnos.
 * Incluye la creación, obtención, actualización y eliminación de sanciones.
 * También vincula sanciones a partes y notifica al tutor legal si corresponde.
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Service
public class SancionServiceImpl implements SancionService {

	@Autowired
	private SancionRepository sancionRepository;

	@Autowired
	private ParteRepository parteRepository;

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private NotificacionController notificacionController;

	/**
	 * Crea una nueva sanción asociada a un parte y un alumno.
	 * Si la sanción implica expulsión, se notifica automáticamente al tutor legal del alumno.
	 * 
	 * @param sancion Objeto sanción con los datos a registrar.
	 * @return Sanción registrada.
	 * @throws ResourceNotFoundException si no se encuentra el parte o el alumno asociado.
	 */
	@Override
	public Sancion createSancion(Sancion sancion) {
	    Parte parteEncontrado = parteRepository.findById(sancion.getParte().getId())
	            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el parte con id: " + sancion.getParte().getId()));

	    Alumno alumnoEncontrado = alumnoRepository.findById(sancion.getAlumno().getDni())
	            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el alumno con id: " + sancion.getAlumno().getDni()));

	    if (sancion.getFecha() == null) {
	        sancion.setFecha(LocalDate.now());
	    }

	    sancion.setAlumno(alumnoEncontrado);
	    parteEncontrado.setSancion(sancion); 
	    sancion.setParte(parteEncontrado);
	    sancionRepository.save(sancion);

	    if (sancion.getTipoSancion() != sancion.getTipoSancion().SIN_EXPULSION) {
	        notificacionController.notificarExpulsionTutorLegal(alumnoEncontrado.getDni());
	    }

	    return sancion;
	}

	/**
	 * Recupera una sanción por su ID.
	 * 
	 * @param id ID de la sanción.
	 * @return Sanción encontrada.
	 * @throws ResourceNotFoundException si no se encuentra la sanción.
	 */
	@Override
	public Sancion getSancionById(Long id) {
		return sancionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: " + id));
	}

	/**
	 * Devuelve una lista con todas las sanciones registradas.
	 * 
	 * @return Lista de sanciones.
	 */
	@Override
	public List<Sancion> getAllSanciones() {
		return sancionRepository.findAll();
	}

	/**
	 * Actualiza una sanción existente con los datos proporcionados.
	 * Solo se modifican los campos no nulos del objeto recibido.
	 * 
	 * @param id ID de la sanción a actualizar.
	 * @param sancion Objeto con los nuevos datos.
	 * @return Sanción actualizada.
	 * @throws ResourceNotFoundException si no se encuentra la sanción.
	 */
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

	/**
	 * Elimina una sanción por su ID.
	 * 
	 * @param id ID de la sanción a eliminar.
	 * @throws ResourceNotFoundException si el ID es nulo.
	 */
	@Override
	public void deleteSancion(Long id) {
		if (id != null) {
			sancionRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado la sanción con id: " + id);
		}
	}

	/**
	 * Devuelve una lista con todas las sanciones registradas de un alumno.
	 *
	 * @return Lista de sanciones.
	 */
	public List<Sancion> getAllSancionesByAlumno(String dni) {
		return sancionRepository.findByAlumnoDni(dni);
	}
}
