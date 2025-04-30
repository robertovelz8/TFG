package com.app.GUZPASEN.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.serviceImpl.AlumnoServiceImpl;

@RestController
@RequestMapping("/guzpasen/conducta/alumno")
public class AlumnoController {
	
	@Autowired
	private AlumnoServiceImpl alumnoServiceImpl;
	
	@PostMapping
	public ResponseEntity<Alumno> createAlumno (@RequestBody Alumno alumno) {
		return new ResponseEntity<Alumno>(alumnoServiceImpl.createAlumno(alumno), HttpStatus.CREATED);
	}
	
	@GetMapping("/{dni}")
	public ResponseEntity<Alumno> getAlumnoById(@PathVariable String dni) {
		return new ResponseEntity<Alumno>(alumnoServiceImpl.getAlumnoById(dni), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Alumno>> getAllAlumnos() {
		return new ResponseEntity<List<Alumno>>(alumnoServiceImpl.getAllAlumnos(), HttpStatus.OK);
	}
	
	@PutMapping("/{dni}")
	public ResponseEntity<Alumno> updateAlumno(@PathVariable String dni, @RequestBody Alumno alumno) {
		return new ResponseEntity<Alumno>(alumnoServiceImpl.updateAlumno(dni, alumno), HttpStatus.OK);
	}
	
	@DeleteMapping("/{dni}")
	public ResponseEntity<Void> deleteAlumno (@PathVariable String dni) {
		alumnoServiceImpl.deleteAlumno(dni);
		return ResponseEntity.ok().build();
	}
}
