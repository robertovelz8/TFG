package com.app.GUZPASEN.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a un alumno dentro del sistema.
 * <p>
 * La clase contiene información personal del alumno, como su nombre, apellidos y datos de contacto del tutor legal.
 * También tiene una lista de sanciones que se le hayan impuesto.
 * </p>
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Alumno {

	@Id
	private String dni;
	
	/**
	 * Nombre del alumno.
	 */
	private String nombre;
	
	/**
	 * Apellidos del alumno.
	 */
	private String apellidos;
	
	/**
	 * Nombre del tutor legal del alumno.
	 */
	private String nombre_tutor_legal;
	
	/**
	 * Apellidos del tutor legal del alumno.
	 */
	private String apellidos_tutor_legal;
	
	/**
	 * Correo electrónico del tutor legal del alumno.
	 */
	private String email_tutor_legal;
	
	/**
	 * Lista de sanciones asociadas a este alumno.
	 */
	@OneToMany (mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Sancion> sanciones = new ArrayList<Sancion>();
}
