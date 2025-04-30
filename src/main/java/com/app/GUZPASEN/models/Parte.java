package com.app.GUZPASEN.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Parte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String motivo;
	
	private LocalDate fecha;
	
	private LocalTime hora;
	
	private String descripcion;
	
	private String lugar;
	
	@OneToOne (mappedBy = "parte", cascade = CascadeType.ALL, orphanRemoval = true)
	private Sancion sancion;
}
