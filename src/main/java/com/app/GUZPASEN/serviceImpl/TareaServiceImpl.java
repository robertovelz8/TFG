package com.app.GUZPASEN.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.controllers.NotificacionController;
import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.models.Tarea;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.repositories.SancionRepository;
import com.app.GUZPASEN.repositories.TareaRepository;
import com.app.GUZPASEN.services.TareaService;

/**
 * Implementación del servicio {@link TareaService} para gestionar tareas asociadas a sanciones.
 * Permite crear, obtener, actualizar y eliminar tareas, así como obtener tareas vinculadas a sanciones con expulsión.
 * También notifica al tutor legal del alumno si corresponde.
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Service
public class TareaServiceImpl implements TareaService {
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private SancionRepository sancionRepository;
	
	@Autowired
	private NotificacionController notificacionController;
	
	@Autowired
	private AlumnoRepository alumnoRepository;

	/**
	 * Crea una nueva tarea asociada a una sanción con expulsión.
	 * Si se crea correctamente, se notifica al tutor legal.
	 * 
	 * @param tarea Objeto Tarea a crear.
	 * @return La tarea creada o null si la sanción no requiere expulsión.
	 * @throws ResourceNotFoundException si no se encuentra la sanción asociada.
	 */
	@Override
	public Tarea createTarea(Tarea tarea) {
		Tarea tareaGuardada = null;

		Sancion sancionEncontrada = sancionRepository.findById(tarea.getSancion().getId())
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: " + tarea.getSancion().getId()));

		if (!sancionEncontrada.getTipoSancion().name().equals("SIN_EXPULSION")) {
			if (tarea.getFechaCreacion() == null) tarea.setFechaCreacion(LocalDate.now());
			if (tarea.getFechaLimite() == null) tarea.setFechaLimite(LocalDate.now().plusYears(1));

			tarea.setSancion(sancionEncontrada);
			tareaGuardada = tareaRepository.save(tarea);

			notificacionController.notificarTareaTutorLegal(tareaGuardada);
		}

		return tareaGuardada;
	}

	/**
	 * Obtiene una tarea por su identificador.
	 * 
	 * @param id ID de la tarea.
	 * @return La tarea correspondiente.
	 * @throws ResourceNotFoundException si no se encuentra la tarea.
	 */
	@Override
	public Tarea getTareaById(Long id) {
		return tareaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la tarea con id: " + id));
	}

	/**
	 * Devuelve una lista con todas las tareas del sistema.
	 * 
	 * @return Lista de tareas.
	 */
	@Override
	public List<Tarea> getAllTareas() {
		return tareaRepository.findAll();
	}

	/**
	 * Actualiza los datos de una tarea existente.
	 * 
	 * @param id    ID de la tarea a actualizar.
	 * @param tarea Objeto con los nuevos datos.
	 * @return La tarea actualizada.
	 * @throws ResourceNotFoundException si no se encuentra la tarea.
	 */
	@Override
	public Tarea updateTarea(Long id, Tarea tarea) {
		Tarea tareaEncontrada = tareaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la tarea con id: " + id));

		if (tarea.getTitulo() != null) tareaEncontrada.setTitulo(tarea.getTitulo());
		if (tarea.getDescripcion() != null) tareaEncontrada.setDescripcion(tarea.getDescripcion());
		if (tarea.getEstado() != null) tareaEncontrada.setEstado(tarea.getEstado());
		if (tarea.getFechaCreacion() != null) tareaEncontrada.setFechaCreacion(tarea.getFechaCreacion());
		if (tarea.getFechaLimite() != null) tareaEncontrada.setFechaLimite(tarea.getFechaLimite());

		return tareaRepository.save(tareaEncontrada);
	}

	/**
	 * Elimina una tarea del sistema.
	 * 
	 * @param id ID de la tarea a eliminar.
	 * @throws ResourceNotFoundException si el ID es nulo.
	 */
	@Override
	public void deleteTarea(Long id) {
		if (id != null) {
			tareaRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado la tarea con id: " + id);
		}
	}

	/**
	 * Obtiene todas las tareas asociadas a sanciones con expulsión de un alumno.
	 * 
	 * @param dni DNI del alumno.
	 * @return Lista de tareas relacionadas con sanciones con expulsión.
	 * @throws ResourceNotFoundException si no se encuentra el alumno.
	 */
	public List<Tarea> getTareasAlumnoExpulsado(String dni) {
		List<Tarea> tareasTotales = new ArrayList<>();

		Alumno alumnoEncontrado = alumnoRepository.findById(dni)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado al alumno con dni: " + dni));

		for (Sancion sancion : alumnoEncontrado.getSanciones()) {
			if (!sancion.getTipoSancion().name().equals("SIN_EXPULSION")) {
				tareasTotales.addAll(sancion.getTareas());
			}
		}
		return tareasTotales;
	}
}
