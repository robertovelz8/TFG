package com.app.GUZPASEN.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.app.GUZPASEN.models.Notificacion;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.app.GUZPASEN.exceptions.CustomException;

public class NotificacionServiceImplTest {

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private TemplateEngine templateEngine;

    @Mock
    private MimeMessage mimeMessage;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void testEnviarCorreoCorrectamente() {
        // Arrange
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario("test@correo.com");
        notificacion.setAsunto("Asunto");
        notificacion.setTitulo("Título");
        notificacion.setCuerpo("Cuerpo");
        notificacion.setFinalMensaje("Saludos");
        notificacion.setMotivo("Motivo");

        when(templateEngine.process(eq("email"), any(Context.class))).thenReturn("<html>Correo</html>");

        // Act & Assert
        assertDoesNotThrow(() -> notificacionService.enviarCorreo(notificacion));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void testEnviarCorreoLanzaExcepcion() {
        // Arrange
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario("error@correo.com");
        notificacion.setAsunto("Asunto");

        when(templateEngine.process(eq("email"), any(Context.class))).thenReturn("<html>Correo</html>");
        doThrow(new RuntimeException("Fallo en envío")).when(mailSender).send(any(MimeMessage.class));

        // Act & Assert
        assertThrows(CustomException.class, () -> notificacionService.enviarCorreo(notificacion));
    }

    @Test
    void testEnviarCorreoEventoActualizadoCorrectamenteConUrl() {
        // Arrange
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario("evento@correo.com");
        notificacion.setAsunto("Evento actualizado");
        notificacion.setTitulo("Título evento");
        notificacion.setCuerpo("El evento ha sido actualizado.");
        notificacion.setFinalMensaje("Saludos");
        notificacion.setCambios("Cambio 1\nCambio 2");

        String url = "https://guzpasen.com/evento";

        when(templateEngine.process(eq("tarea"), any(Context.class))).thenReturn("<html>Correo evento</html>");

        // Act & Assert
        assertDoesNotThrow(() -> notificacionService.enviarCorreoEventoActualizado(notificacion, url));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void testEnviarCorreoEventoActualizadoSinUrl() {
        // Arrange
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario("sinurl@correo.com");
        notificacion.setAsunto("Evento sin URL");
        notificacion.setTitulo("Título sin URL");
        notificacion.setCuerpo("Cuerpo");
        notificacion.setFinalMensaje("Final");
        notificacion.setCambios("Cambio único");

        when(templateEngine.process(eq("tarea"), any(Context.class))).thenReturn("<html>Correo sin URL</html>");

        // Act & Assert
        assertDoesNotThrow(() -> notificacionService.enviarCorreoEventoActualizado(notificacion, null));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void testEnviarCorreoEventoActualizadoLanzaExcepcion() {
        // Arrange
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario("error@correo.com");
        notificacion.setAsunto("Error");
        notificacion.setTitulo("T");
        notificacion.setCuerpo("C");
        notificacion.setFinalMensaje("F");
        notificacion.setCambios("X");

        when(templateEngine.process(eq("tarea"), any(Context.class))).thenReturn("<html>Error</html>");
        doThrow(new RuntimeException("Error envío")).when(mailSender).send(any(MimeMessage.class));

        // Act & Assert
        assertThrows(CustomException.class, () -> notificacionService.enviarCorreoEventoActualizado(notificacion, ""));
    }
}

