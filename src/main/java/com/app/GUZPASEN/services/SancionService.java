package com.app.GUZPASEN.services;

import java.util.List;

import com.app.GUZPASEN.models.Sancion;

public interface SancionService {

	public Sancion createSancion (Sancion Sancion);
	public Sancion getSancionById (Long id);
	public List<Sancion> getAllSanciones ();
	public Sancion updateSancion (Sancion Sancion);
	public void deleteSancion (Long id);
}
