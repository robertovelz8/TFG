package com.app.GUZPASEN.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Sancion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate fecha;

	@Enumerated(EnumType.STRING)
	private TipoSancion tipoSancion;

	private String duracion;

	@OneToMany(mappedBy = "sancion", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Tarea> tareas = new ArrayList<Tarea>();

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "alumno", referencedColumnName = "dni")
	private Alumno alumno;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "parte")
	private Parte parte;

	
	public enum TipoSancion {
		CON_EXPULSION_DENTRO, CON_EXPULSION_FUERA, SIN_EXPULSION
	}
}
