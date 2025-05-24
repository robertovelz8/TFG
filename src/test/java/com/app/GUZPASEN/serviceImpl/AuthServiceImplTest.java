package com.app.GUZPASEN.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.app.GUZPASEN.DTOs.*;
import com.app.GUZPASEN.config.JwtService;
import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Rol;
import com.app.GUZPASEN.models.Usuario;
import com.app.GUZPASEN.repositories.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_successful() {
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Juan");
        request.setApellidos("Pérez");
        request.setEmail("juan@example.com");
        request.setClave("1234");

        when(usuarioRepository.existsByEmail("juan@example.com")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("hashed_password");

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .apellidos("Pérez")
                .email("juan@example.com")
                .clave("hashed_password")
                .rol(Rol.PROFESOR)
                .usuario_movil(false)
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        RegisterRequestDTO result = authService.register(request);

        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellidos());
        assertEquals("juan@example.com", result.getEmail());
        assertEquals(Rol.PROFESOR, result.getRol());
        assertFalse(result.isUsuario_movil());
    }

    @Test
    void testRegister_emailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("juan@example.com");

        when(usuarioRepository.existsByEmail("juan@example.com")).thenReturn(true);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            authService.register(request);
        });

        assertEquals("El email ya está en uso", ex.getMessage());
    }

    @Test
    void testAuthenticate_successful() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("juan@example.com");
        request.setClave("1234");

        Usuario usuario = Usuario.builder()
                .email("juan@example.com")
                .clave("hashed_password")
                .rol(Rol.PROFESOR)
                .build();

        when(usuarioRepository.findUserByEmail("juan@example.com")).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn("jwt-token");

        // Corregido aquí
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        AuthResponse response = authService.authenticate(request);

        assertEquals("jwt-token", response.getToken());
    }


    @Test
    void testAuthenticate_userNotFound() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("noexiste@example.com");
        request.setClave("1234");

        when(usuarioRepository.findUserByEmail("noexiste@example.com")).thenReturn(Optional.empty());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        assertThrows(NoSuchElementException.class, () -> {
            authService.authenticate(request);
        });
    }

}

