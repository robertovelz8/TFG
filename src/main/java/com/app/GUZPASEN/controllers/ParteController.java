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

import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.serviceImpl.ParteServiceImpl;

@RestController
@RequestMapping("/guzpasen/conducta/parte")
public class ParteController {
	
	@Autowired
	private ParteServiceImpl parteServiceImpl;
	
	@PostMapping
	public ResponseEntity<Parte> crearParte (@RequestBody Parte parte) {
		return new ResponseEntity<Parte>(parteServiceImpl.createParte(parte), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Parte> getParteById (@PathVariable Long id)  {
		return new ResponseEntity<Parte>(parteServiceImpl.getParteById(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Parte>> getAllPartes ()  {
		return new ResponseEntity<List<Parte>>(parteServiceImpl.getAllPartes(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Parte> updateParte (@RequestBody Parte parte) {
		return new ResponseEntity<Parte>(parteServiceImpl.updateParte(parte), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteParte (@PathVariable Long id) {
		return ResponseEntity.ok().build();
	}

}
