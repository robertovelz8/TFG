package com.app.GUZPASEN.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	private String nombre;
	private String apellidos;
	private String email;
	private String clave;
}
