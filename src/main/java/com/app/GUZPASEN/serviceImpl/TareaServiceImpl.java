package com.app.GUZPASEN.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.models.Tarea;
import com.app.GUZPASEN.repositories.SancionRepository;
import com.app.GUZPASEN.repositories.TareaRepository;
import com.app.GUZPASEN.services.TareaService;

@Service
public class TareaServiceImpl implements TareaService {
	
	@Autowired
	private TareaRepository tareaRepository;
	
	@Autowired
	private SancionRepository sancionRepository;

	@Override
	public Tarea createTarea(Tarea tarea) {
		
		Sancion sancionEncontrada = sancionRepository.findById(tarea.getSancion().getId())
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: "+tarea.getSancion().getId()));
		
		if (tarea.getFechaCreacion() == null) tarea.setFechaCreacion(LocalDate.now());
		if (tarea.getFechaLimite() == null) tarea.setFechaLimite(LocalDate.now().plusYears(1));
		
		tarea.setSancion(sancionEncontrada);
		
		return tareaRepository.save(tarea);
		
		
	}

	@Override
	public Tarea getTareaById(Long id) {
		return tareaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la tarea con id: "+id));
	}

	@Override
	public List<Tarea> getAllTareas() {
		return tareaRepository.findAll();
	}

	@Override
	public Tarea updateTarea(Long id, Tarea tarea) {
		Tarea tareaEncontrada = tareaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la tarea con id: "+id));
		
		if (tarea.getTitulo() != null) tareaEncontrada.setTitulo(tarea.getTitulo());
		if (tarea.getDescripcion() != null) tareaEncontrada.setDescripcion(tarea.getDescripcion());
		if (tarea.getEstado() != null) tareaEncontrada.setEstado(tarea.getEstado());
		if (tarea.getFechaCreacion() != null) tareaEncontrada.setFechaCreacion(tarea.getFechaCreacion());
		if (tarea.getFechaLimite() != null) tareaEncontrada.setFechaLimite((tarea.getFechaLimite()));
		

		return tareaRepository.save(tareaEncontrada);
	}

	@Override
	public void deleteTarea(Long id) {
		
		if (id != null) {
			tareaRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado la tarea con id: "+id);
		}

	}

}
