package com.app.GUZPASEN.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.app.GUZPASEN.exceptions.CustomException;
import com.app.GUZPASEN.models.Notificacion;

import jakarta.mail.internet.MimeMessage;

@Service
public class NotificacionServiceImpl {

	@Autowired
    private final JavaMailSender mailSender;
	
	@Autowired
    private final TemplateEngine templateEngine;

    @Autowired
    public NotificacionServiceImpl (JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    public void enviarCorreo(Notificacion notificacion) {
        try {
            Context context = new Context();
            context.setVariable("titulo", notificacion.getTitulo());
            context.setVariable("cuerpo", notificacion.getCuerpo());
            context.setVariable("finalMensaje", notificacion.getFinalMensaje());
            context.setVariable("motivo", notificacion.getMotivo());


            String cuerpoHTML = templateEngine.process("email", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(notificacion.getDestinatario());
            helper.setSubject(notificacion.getAsunto());
            helper.setText(cuerpoHTML, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new CustomException("Error al enviar el correo.");
        }
    }
    
    public void enviarCorreoEventoActualizado(Notificacion notificacion, String url) {
        try {
            Context context = new Context();
            context.setVariable("titulo", notificacion.getTitulo());
            context.setVariable("cuerpo", notificacion.getCuerpo());
            context.setVariable("finalMensaje", notificacion.getFinalMensaje());

            // Formatear cambios como lista HTML
            String[] cambios = notificacion.getCambios().split("\n");
            StringBuilder cambiosHtml = new StringBuilder();
            for (String cambio : cambios) {
                cambiosHtml.append("<li>").append(cambio.trim()).append("</li>");
            }
            context.setVariable("cambiosHtml", cambiosHtml.toString());

            if (url != null && !url.isEmpty()) {
                context.setVariable("url", url);
            }

            String cuerpoHTML = templateEngine.process("tarea", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(notificacion.getDestinatario());
            helper.setSubject(notificacion.getAsunto());
            helper.setText(cuerpoHTML, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new CustomException("Error al enviar el correo.");
        }
    }
    
}
