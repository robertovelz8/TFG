package com.app.GUZPASEN.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una tarea asignada como parte de una sanción impuesta a un alumno.
 * <p>
 * La clase contiene detalles sobre el título, descripción, estado, fechas de creación y límite de la tarea,
 * así como la sanción a la que está asociada.
 * </p>
 * 
 * @author Roberto Velázquez Vázquez
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Tarea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Título de la tarea.
	 * Describe de manera breve la actividad o acción que se debe realizar.
	 */
	private String titulo;
	
	/**
	 * Descripción detallada de la tarea.
	 * Proporciona información adicional sobre lo que se espera de la tarea.
	 */
	private String descripcion;
	
	/**
	 * Estado actual de la tarea.
	 * El estado puede ser uno de los siguientes: 
	 * <ul>
	 *     <li>COMPLETADA</li>
	 *     <li>EN_PROGRESO</li>
	 *     <li>PENDIENTE</li>
	 * </ul>
	 */
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	/**
	 * Fecha de creación de la tarea.
	 * Indica cuándo fue asignada o creada la tarea.
	 */
	private LocalDate fechaCreacion;
	
	/**
	 * Fecha límite para completar la tarea.
	 * Indica el plazo máximo para finalizar la tarea.
	 */
	private LocalDate fechaLimite;
	
	/**
	 * Sanción asociada a esta tarea.
	 * Una tarea siempre estará vinculada a una sanción específica.
	 */
	@ManyToOne
	@JoinColumn(name = "sancion")
	private Sancion sancion;
	
	/**
	 * Enumeración que define los posibles estados de una tarea.
	 */
	public enum Estado {
		COMPLETADA,   // La tarea ha sido completada.
		EN_PROGRESO,  // La tarea está en curso.
		PENDIENTE     // La tarea aún no ha comenzado.
	}
}
