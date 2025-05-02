package com.app.GUZPASEN.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa un parte disciplinario relacionado con una sanción.
 * <p>
 * Un parte contiene información sobre el motivo, fecha, hora, descripción y lugar donde ocurrió el incidente.
 * Además, está asociado a una sanción específica.
 * </p>
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Parte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Motivo del parte disciplinario.
	 * Este campo es obligatorio.
	 */
	@NotNull
	private String motivo;
	
	/**
	 * Fecha en la que se registró el parte disciplinario.
	 */
	private LocalDate fecha;
	
	/**
	 * Hora en la que se registró el parte disciplinario.
	 */
	private LocalTime hora;
	
	/**
	 * Descripción detallada del parte disciplinario.
	 */
	private String descripcion;
	
	/**
	 * Lugar donde ocurrió el incidente.
	 */
	private String lugar;
	
	/**
	 * Sanción asociada a este parte. Si se elimina el parte, también se elimina la sanción.
	 */
	@OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "sancion_id")
	private Sancion sancion;
}
