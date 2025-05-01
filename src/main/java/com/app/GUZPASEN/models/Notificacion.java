package com.app.GUZPASEN.models;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Service
@NoArgsConstructor
public class Notificacion {
    private String nombreApp = "GUZPASEN";
    private String asunto = "Tiene una notificación nueva en GUZPASEN.";
    private String finalMensaje = "Atentamente, " + nombreApp;
    private String destinatario;
    private String cuerpo;
    private String titulo;
    private String motivo;
    private String cambios;
}
