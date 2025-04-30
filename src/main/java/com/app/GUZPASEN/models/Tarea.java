package com.app.GUZPASEN.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tarea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String titulo;
	
	private String descripcion;
	
	private Estado estado;
	
	private LocalDate fechaCreacion;
	
	private LocalDate fechaLimite;
	
	@ManyToOne
	@JoinColumn (name = "sancion_id")
	private Sancion id_sancion;
	
	public enum Estado {
		COMPLETADA,
		EN_PROGRESO,
		PENDIENTE
	}
}
