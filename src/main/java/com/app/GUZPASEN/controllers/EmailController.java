package com.app.GUZPASEN.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.GUZPASEN.config.JwtService;
import com.app.GUZPASEN.models.Notificacion;
import com.app.GUZPASEN.services.NotificacionServiceImpl;

@Component
public class EmailController {
	
	@Autowired
	private NotificacionServiceImpl notificacionServiceImpl;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private Notificacion notificacion;
	
	//TODO
	public void notificarSancionPadres() {
		
	}

}
