package com.app.GUZPASEN.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Alumno {

	@Id
	private String dni;
	
	private String nombre;
	private String apellidos;
	private String nombre_tutor_legal;
	private String apellidos_tutor_legal;
	private String email_tutor_legal;
	
	@OneToMany (mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Sancion> sanciones = new ArrayList<Sancion>();
	
}
