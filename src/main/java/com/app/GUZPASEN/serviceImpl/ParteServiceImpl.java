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

@Service
public class ParteServiceImpl implements ParteService {
	
	@Autowired
	private ParteRepository parteRepository;

	@Override
	public Parte createParte(Parte parte) {
		
		if(parte.getFecha() == null) {
			parte.setFecha(LocalDate.now());
		}
		
		if (parte.getHora() == null) {
			parte.setHora(LocalTime.now());
		}
		
		return parteRepository.save(parte);
	}

	@Override
	public Parte getParteById(Long id) {
		return parteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el parte con id: "+id));
	}

	@Override
	public List<Parte> getAllPartes() {
		return parteRepository.findAll();
	}

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


	@Override
	public void deleteParte(Long id) {
		
		if (id != null) {
			parteRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("No se ha encontrado el parte con id: "+id);
		}
		
	}

}
