package com.app.GUZPASEN.services;

import java.util.List;
import com.app.GUZPASEN.models.Sancion;

public interface SancionService {

	public Sancion createSancion (Sancion sancion);
	public Sancion getSancionById (Long id);
	public List<Sancion> getAllSanciones ();
	public Sancion updateSancion (Long id, Sancion Sancion);
	public void deleteSancion (Long id);
}
