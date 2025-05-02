package com.app.GUZPASEN.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Notificacion;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.models.Tarea;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.serviceImpl.NotificacionServiceImpl;

@Component
public class NotificacionController {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private NotificacionServiceImpl notificacionServiceImpl;
	
    public void notificarExpulsionTutorLegal(String dni) {
        Alumno alumno = alumnoRepository.findById(dni)
            .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));

        String email = alumno.getEmail_tutor_legal();
        if (email == null || email.isBlank()) {
            throw new ResourceNotFoundException("El alumno no tiene email del tutor legal");
        }

        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario(email);
        notificacion.setAsunto("Nueva expulsión.");
        notificacion.setTitulo("Estimado "+alumno.getNombre_tutor_legal()+ " "+ alumno.getApellidos_tutor_legal()+", tiene una notificación nueva.");
        notificacion.setCuerpo("Tu hijo " + alumno.getNombre() + " " + alumno.getApellidos() + " ha sido expulsado.");
        notificacionServiceImpl.enviarCorreo(notificacion); 
    }
    
    public void notificarTareaTutorLegal(Tarea tarea) {
        Alumno alumno = alumnoRepository.findById(tarea.getSancion().getAlumno().getDni())
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));

            String email = alumno.getEmail_tutor_legal();
            if (email == null || email.isBlank()) {
                throw new ResourceNotFoundException("El alumno no tiene email del tutor legal");
            }

            Notificacion notificacion = new Notificacion();
            notificacion.setDestinatario(email);
            notificacion.setAsunto("Creación de nueva tarea.");
            notificacion.setTitulo("Estimado "+alumno.getNombre_tutor_legal()+ " "+ alumno.getApellidos_tutor_legal()+", tiene una notificación nueva.");
            notificacion.setCuerpo("Tu hijo " + alumno.getNombre() + " " + alumno.getApellidos() + " tiene una nueva tarea.");
            notificacionServiceImpl.enviarCorreo(notificacion); 
    }

}
