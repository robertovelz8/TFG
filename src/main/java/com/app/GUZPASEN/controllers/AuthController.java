package com.app.GUZPASEN.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.GUZPASEN.DTOs.AuthResponse;
import com.app.GUZPASEN.DTOs.AuthenticationRequest;
import com.app.GUZPASEN.DTOs.RegisterRequest;
import com.app.GUZPASEN.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/guzpasen/auth")
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
    AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request, BindingResult result) {
		if (result.hasErrors()) {
	        Map<String, String> errors = new HashMap<>();
	        result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

	        return ResponseEntity.badRequest().body(errors);
	    }
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthResponse> authenticate (@RequestBody @Valid AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
}
