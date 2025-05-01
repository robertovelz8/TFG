package com.app.GUZPASEN.services;

import java.util.List;

import com.app.GUZPASEN.models.Tarea;

public interface TareaService {
	
	public Tarea createTarea (Tarea Tarea);
	public Tarea getTareaById (Long id);
	public List<Tarea> getAllTareas ();
	public Tarea updateTarea (Long id, Tarea Tarea);
	public void deleteTarea (Long id);

}
