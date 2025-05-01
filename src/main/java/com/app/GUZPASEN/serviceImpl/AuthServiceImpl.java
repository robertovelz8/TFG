package com.app.GUZPASEN.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.GUZPASEN.DTOs.AuthResponse;
import com.app.GUZPASEN.DTOs.AuthenticationRequest;
import com.app.GUZPASEN.DTOs.RegisterRequest;
import com.app.GUZPASEN.DTOs.RegisterRequestDTO;
import com.app.GUZPASEN.config.JwtService;
import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Rol;
import com.app.GUZPASEN.models.Usuario;
import com.app.GUZPASEN.repositories.UsuarioRepository;
import com.app.GUZPASEN.services.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Autowired
	private final UsuarioRepository usuarioRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final JwtService jwtService;

	@Autowired
	private final AuthenticationManager authenticationManager;

	@Override
	public RegisterRequestDTO register(RegisterRequest request) {
		if (usuarioRepository.existsByEmail(request.getEmail())) {
			throw new ResourceNotFoundException("El email ya está en uso");
		}

		var user = Usuario.builder().nombre(request.getNombre()).apellidos(request.getApellidos())
				.email(request.getEmail()).clave(passwordEncoder.encode(request.getClave())).rol(Rol.PROFESOR).usuario_movil(false).build();
		usuarioRepository.save(user);

		return new RegisterRequestDTO(user.getId(), user.getNombre(), user.getApellidos(), user.getEmail(),
				user.getRol(), user.isUsuario_movil());
	}

	@Override
	public AuthResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(

				request.getEmail(), request.getClave()));
		var user = usuarioRepository.findUserByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthResponse.builder().token(jwtToken).build();
	}
}
