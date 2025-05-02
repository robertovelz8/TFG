package com.app.GUZPASEN.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.repositories.ParteRepository;
import com.app.GUZPASEN.services.ParteService;

/**
 * Implementación del servicio {@link ParteService} para la gestión de partes disciplinarios.
 * Proporciona métodos para crear, obtener, actualizar y eliminar partes.
 * 
 * <p>Al crear un parte, si no se especifican la fecha o la hora, se asignan automáticamente
 * la fecha y hora actuales.</p>
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Service
public class ParteServiceImpl implements ParteService {
	
	@Autowired
	private ParteRepository parteRepository;

	/**
	 * Crea un nuevo parte en la base de datos.
	 * Si la fecha u hora no están definidas, se asignan con los valores actuales.
	 * 
	 * @param parte Parte a crear.
	 * @return Parte creado y guardado.
	 */
	@Override
	public Parte createParte(Parte parte) {
		if (parte.getFecha() == null) {
			parte.setFecha(LocalDate.now());
		}
		
		if (parte.getHora() == null) {
			parte.setHora(LocalTime.now());
		}
		
		return parteRepository.save(parte);
	}

	/**
	 * Obtiene un parte por su ID.
	 * 
	 * @param id ID del parte.
	 * @return Parte correspondiente al ID.
	 * @throws ResourceNotFoundException si no se encuentra el parte.
	 */
	@Override
	public Parte getParteById(Long id) {
		return parteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el parte con id: " + id));
	}

	/**
	 * Obtiene la lista completa de partes registrados.
	 * 
	 * @return Lista de partes.
	 */
	@Override
	public List<Parte> getAllPartes() {
		return parteRepository.findAll();
	}

	/**
	 * Actualiza los datos de un parte existente.
	 * Solo se actualizan los campos no nulos del parte proporcionado.
	 * 
	 * @param id ID del parte a actualizar.
	 * @param parte Parte con los nuevos datos.
	 * @return Parte actualizado.
	 * @throws ResourceNotFoundException si no se encuentra el parte.
	 */
	public Parte updateParte(Long id, Parte parte) {
	    Parte parteEncontrado = parteRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el parte con id: " + id));

	    if (parte.getMotivo() != null) parteEncontrado.setMotivo(parte.getMotivo());
	    if (parte.getDescripcion() != null) parteEncontrado.setDescripcion(parte.getDescripcion());
	    if (parte.getFecha() != null) parteEncontrado.setFecha(parte.getFecha());
	    if (parte.getHora() != null) parteEncontrado.setHora(parte.getHora());
	    if (parte.getLugar() != null) parteEncontrado.setLugar(parte.getLugar());

	    return parteRepository.save(parteEncontrado);
	}

	/**
	 * Elimina un parte por su ID.
	 * 
	 * @param id ID del parte a eliminar.
	 * @throws ResourceNotFoundException si el ID es nulo.
	 */
	@Override
	public void deleteParte(Long id) {
		if (id != null) {
			parteRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado el parte con id: " + id);
		}
	}
}
