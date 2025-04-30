package com.app.GUZPASEN.services;

import java.util.List;

import com.app.GUZPASEN.models.Parte;

public interface ParteService {

	public Parte createParte (Parte parte);
	public Parte getParteById (Long id);
	public List<Parte> getAllPartes ();
	public Parte updateParte (Parte parte);
	public void deleteParte (Long id);
}
