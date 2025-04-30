package com.app.GUZPASEN.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sancion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate fecha;

	private TipoSancion tipoSancion;

	private String duracion;

	@OneToMany(mappedBy = "id_sancion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tarea> tareas = new ArrayList<Tarea>();

	@ManyToOne
	@JoinColumn (name = "alumno_dni")
	private Alumno alumno_sancionado;

	@OneToOne
	@MapsId
	@JoinColumn(name = "parte_id")
	private Parte parte_id;

	public enum TipoSancion {
		CON_EXPULSION_DENTRO, CON_EXPULSION_FUERA, SIN_EXPULSION
	}
}
