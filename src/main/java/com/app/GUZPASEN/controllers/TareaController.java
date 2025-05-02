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

import com.app.GUZPASEN.models.Tarea;
import com.app.GUZPASEN.serviceImpl.TareaServiceImpl;

@RestController
@RequestMapping("/guzpasen/conducta/tarea")
public class TareaController {
	
	@Autowired
	private TareaServiceImpl tareaServiceImpl;
	
	@PostMapping
	public ResponseEntity<Tarea> createTarea (@RequestBody Tarea tarea) {
		return new ResponseEntity<Tarea>(tareaServiceImpl.createTarea(tarea), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tarea> getTareaById (@PathVariable Long id) {
		return new ResponseEntity<Tarea>(tareaServiceImpl.getTareaById(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Tarea>> getAllTareas () {
		return new ResponseEntity<List<Tarea>>(tareaServiceImpl.getAllTareas(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Tarea> updateTarea (@PathVariable Long id, @RequestBody Tarea tarea) {
		return new ResponseEntity<Tarea>(tareaServiceImpl.updateTarea(id, tarea), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteTarea (@PathVariable Long id) {
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/tareas-alumno-expulsado/{dni}")
	public ResponseEntity<List<Tarea>> getTareasAlumnoExpulsado (@PathVariable String dni) {
		return new ResponseEntity<List<Tarea>>(tareaServiceImpl.getTareasAlumnoExpulsado(dni), HttpStatus.OK);
	}
}
