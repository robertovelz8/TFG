package com.app.GUZPASEN.DTOs;

import com.app.GUZPASEN.models.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String email;
    private Rol rol;
    private boolean usuario_movil;
}
