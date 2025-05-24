package com.app.GUZPASEN.controllers;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Notificacion;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.models.Tarea;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.serviceImpl.NotificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionControllerTest {

    private NotificacionController notificacionController;
    private AlumnoRepository alumnoRepository;
    private NotificacionServiceImpl notificacionServiceImpl;

    @BeforeEach
    void setUp() {
        alumnoRepository = mock(AlumnoRepository.class);
        notificacionServiceImpl = mock(NotificacionServiceImpl.class);
        notificacionController = new NotificacionController();
        // inyectar mocks en el controller
        notificacionController.alumnoRepository = alumnoRepository;
        notificacionController.notificacionServiceImpl = notificacionServiceImpl;
    }

    @Test
    void testNotificarExpulsionTutorLegal_success() {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setEmail_tutor_legal("tutor@example.com");
        alumno.setNombre_tutor_legal("Juan");
        alumno.setApellidos_tutor_legal("Pérez");
        alumno.setNombre("Carlos");
        alumno.setApellidos("García");

        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));

        notificacionController.notificarExpulsionTutorLegal("12345678A");

        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionServiceImpl, times(1)).enviarCorreo(captor.capture());

        Notificacion notif = captor.getValue();
        assertEquals("tutor@example.com", notif.getDestinatario());
        assertEquals("Nueva expulsión.", notif.getAsunto());
        assertTrue(notif.getTitulo().contains("Juan Pérez"));
        assertTrue(notif.getCuerpo().contains("Carlos García"));
    }

    @Test
    void testNotificarExpulsionTutorLegal_alumnoNoExiste_throwsException() {
        when(alumnoRepository.findById("noexiste")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                notificacionController.notificarExpulsionTutorLegal("noexiste")
        );

        assertEquals("Alumno no encontrado", ex.getMessage());
        verifyNoInteractions(notificacionServiceImpl);
    }

    @Test
    void testNotificarExpulsionTutorLegal_sinEmailTutor_throwsException() {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setEmail_tutor_legal("");
        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                notificacionController.notificarExpulsionTutorLegal("12345678A")
        );

        assertEquals("El alumno no tiene email del tutor legal", ex.getMessage());
        verifyNoInteractions(notificacionServiceImpl);
    }

    @Test
    void testNotificarTareaTutorLegal_success() {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setEmail_tutor_legal("tutor@example.com");
        alumno.setNombre_tutor_legal("Ana");
        alumno.setApellidos_tutor_legal("Martínez");
        alumno.setNombre("Laura");
        alumno.setApellidos("Sánchez");

        Sancion sancion = new Sancion();
        sancion.setAlumno(alumno);

        Tarea tarea = new Tarea();
        tarea.setSancion(sancion);

        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));

        notificacionController.notificarTareaTutorLegal(tarea);

        ArgumentCaptor<Notificacion> captor = ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionServiceImpl, times(1)).enviarCorreo(captor.capture());

        Notificacion notif = captor.getValue();
        assertEquals("tutor@example.com", notif.getDestinatario());
        assertEquals("Creación de nueva tarea.", notif.getAsunto());
        assertTrue(notif.getTitulo().contains("Ana Martínez"));
        assertTrue(notif.getCuerpo().contains("Laura Sánchez"));
    }

    @Test
    void testNotificarTareaTutorLegal_alumnoNoExiste_throwsException() {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678X");

        Sancion sancion = new Sancion();
        sancion.setAlumno(alumno);

        Tarea tarea = new Tarea();
        tarea.setSancion(sancion);

        when(alumnoRepository.findById("12345678X")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                notificacionController.notificarTareaTutorLegal(tarea)
        );

        assertEquals("Alumno no encontrado", ex.getMessage());
        verifyNoInteractions(notificacionServiceImpl);
    }


    @Test
    void testNotificarTareaTutorLegal_sinEmailTutor_throwsException() {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setEmail_tutor_legal("");
        Sancion sancion = new Sancion();
        sancion.setAlumno(alumno);
        Tarea tarea = new Tarea();
        tarea.setSancion(sancion);

        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                notificacionController.notificarTareaTutorLegal(tarea)
        );

        assertEquals("El alumno no tiene email del tutor legal", ex.getMessage());
        verifyNoInteractions(notificacionServiceImpl);
    }
}
