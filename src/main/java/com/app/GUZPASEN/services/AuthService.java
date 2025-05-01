package com.app.GUZPASEN.services;

import com.app.GUZPASEN.DTOs.AuthResponse;
import com.app.GUZPASEN.DTOs.AuthenticationRequest;
import com.app.GUZPASEN.DTOs.RegisterRequest;
import com.app.GUZPASEN.DTOs.RegisterRequestDTO;

public interface AuthService {
	public RegisterRequestDTO register (RegisterRequest request);
	public AuthResponse authenticate (AuthenticationRequest request);
	
}
