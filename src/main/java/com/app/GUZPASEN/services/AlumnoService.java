package com.app.GUZPASEN.services;

import java.util.List;

import com.app.GUZPASEN.models.Alumno;

public interface AlumnoService {

	public Alumno createAlumno (Alumno alumno);
	public Alumno getAlumnoById (String dni);
	public List<Alumno> getAllAlumnos ();
	public Alumno updateAlumno (String dni, Alumno alumno);
	public void deleteAlumno (String dni);
}
