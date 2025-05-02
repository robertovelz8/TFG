package com.app.GUZPASEN.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.GUZPASEN.DTOs.ParteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Representa una sanción impuesta a un alumno.
 * <p>
 * Una sanción incluye detalles como la fecha de la sanción, el tipo de sanción, su duración,
 * las tareas relacionadas y el alumno al que se le impuso. También puede estar asociada a un parte disciplinario.
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
public class Sancion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Fecha en la que se impuso la sanción.
	 */
	private LocalDate fecha;

	/**
	 * Tipo de la sanción (puede ser con o sin expulsión).
	 */
	@Enumerated(EnumType.STRING)
	private TipoSancion tipoSancion;

	/**
	 * Duración de la sanción (puede ser en días o en horas).
	 */
	private String duracion;

	/**
	 * Lista de tareas asignadas al alumno como parte de la sanción.
	 */
	@OneToMany(mappedBy = "sancion", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Tarea> tareas = new ArrayList<Tarea>();

	/**
	 * Alumno al que se le impuso la sanción.
	 */
	@ManyToOne
	@JoinColumn(name = "alumno", referencedColumnName = "dni")
	private Alumno alumno;

	/**
	 * Parte disciplinario asociado a esta sanción.
	 * Se asigna un parte a la sanción si existe.
	 */
	@OneToOne (mappedBy = "sancion")
	private Parte parte;
	
	/**
	 * Obtiene un DTO del parte asociado a la sanción.
	 * 
	 * @return El DTO del parte, si existe.
	 */
    @JsonProperty("parte")
    public ParteDTO getParteDTO() {
        if (parte != null) {
            return new ParteDTO(parte.getId(), parte.getMotivo());
        }
        return null;
    }

	/**
	 * Tipos de sanciones disponibles: con expulsión dentro, con expulsión fuera, o sin expulsión.
	 */
	public enum TipoSancion {
		CON_EXPULSION_DENTRO, CON_EXPULSION_FUERA, SIN_EXPULSION
	}
}
