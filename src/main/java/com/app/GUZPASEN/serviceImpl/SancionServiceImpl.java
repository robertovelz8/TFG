package com.app.GUZPASEN.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.repositories.SancionRepository;
import com.app.GUZPASEN.services.SancionService;

@Service
public class SancionServiceImpl implements SancionService {
	
	@Autowired
	private SancionRepository sancionRepository;

	@Override
	public Sancion createSancion(Sancion sancion) {
		return sancionRepository.save(sancion);
	}

	@Override
	public Sancion getSancionById(Long id) {
		return sancionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: "+id));
	}

	@Override
	public List<Sancion> getAllSanciones() {
		return sancionRepository.findAll();
	}

	@Override
	public Sancion updateSancion(Long id, Sancion sancion) {
		Sancion sancionEncontrada = sancionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la sanción con id: "+id));
		
		if (sancion.getDuracion() != null) sancionEncontrada.setDuracion(sancion.getDuracion());
		if (sancion.getFecha() != null) sancionEncontrada.setFecha(sancion.getFecha());
		if (sancion.getTipoSancion() != null) sancionEncontrada.setTipoSancion(sancion.getTipoSancion());
		
		return sancionRepository.save(sancion);
	}

	@Override
	public void deleteSancion(Long id) {
		 
		if(id != null) {
			sancionRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado la sanción con id: "+id);
		}

	}

}
