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
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.serviceImpl.SancionServiceImpl;

@RestController
@RequestMapping("/guzpasen/conducta/sancion")
public class SancionController {
	
	@Autowired
	private SancionServiceImpl sancionServiceImpl;
	
	@PostMapping
	public ResponseEntity<Sancion> crearSancion (@RequestBody Sancion sancion) {
		return new ResponseEntity<Sancion>(sancionServiceImpl.createSancion(sancion), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Sancion> getSancionById (@PathVariable Long id) {
		return new ResponseEntity<Sancion>(sancionServiceImpl.getSancionById(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Sancion>> getAllSanciones () {
		return new ResponseEntity<List<Sancion>>(sancionServiceImpl.getAllSanciones(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Sancion> updateSancion (@PathVariable Long id, @RequestBody Sancion sancion) {
		return new ResponseEntity<Sancion>(sancionServiceImpl.updateSancion(id, sancion), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSancion (@PathVariable Long id) {
		sancionServiceImpl.deleteSancion(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/alumno/{dni}")
	public ResponseEntity<List<Sancion>> getAllSancionesByAlumno (@PathVariable String dni) {
		return new ResponseEntity<List<Sancion>>(sancionServiceImpl.getAllSancionesByAlumno(dni), HttpStatus.OK);
	}
}
