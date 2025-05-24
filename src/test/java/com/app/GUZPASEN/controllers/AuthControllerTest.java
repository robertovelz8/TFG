package com.app.GUZPASEN.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import java.util.HashMap;
import java.util.Map;

import com.app.GUZPASEN.DTOs.AuthResponse;
import com.app.GUZPASEN.DTOs.AuthenticationRequest;
import com.app.GUZPASEN.DTOs.RegisterRequest;
import com.app.GUZPASEN.DTOs.RegisterRequestDTO;
import com.app.GUZPASEN.controllers.AuthController;
import com.app.GUZPASEN.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_ShouldReturnOk_WhenNoValidationErrors() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setNombre("Juan");
        request.setApellidos("Perez");
        request.setEmail("juan@example.com");
        request.setClave("123456");

        var registerRequestDTO = mock(RegisterRequestDTO.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(authService.register(request)).thenReturn(registerRequestDTO);

        // Act
        ResponseEntity<?> response = authController.register(request, bindingResult);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(registerRequestDTO, response.getBody());
        verify(authService).register(request);
    }

    @Test
    public void register_ShouldReturnBadRequest_WhenValidationErrors() {
        // Arrange
        RegisterRequest request = new RegisterRequest();

        when(bindingResult.hasErrors()).thenReturn(true);
        FieldError error1 = new FieldError("registerRequest", "nombre", "El nombre es obligatorio");
        FieldError error2 = new FieldError("registerRequest", "email", "Email no válido");
        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(error1, error2));

        // Act
        ResponseEntity<?> response = authController.register(request, bindingResult);

        // Assert
        assertEquals(BAD_REQUEST, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody();

        assertEquals(2, errors.size());
        assertEquals("El nombre es obligatorio", errors.get("nombre"));
        assertEquals("Email no válido", errors.get("email"));

        verify(authService, never()).register(any());
    }

    @Test
    public void authenticate_ShouldReturnOk_WhenValidRequest() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("user@example.com");
        request.setClave("password123");

        AuthResponse authResponse = AuthResponse.builder()
                .token("jwt-token-example")
                .build();

        when(authService.authenticate(request)).thenReturn(authResponse);

        // Act
        ResponseEntity<AuthResponse> response = authController.authenticate(request);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
        verify(authService).authenticate(request);
    }

    @Test
    public void authenticate_ShouldThrowException_WhenInvalidCredentials() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("user@example.com");
        request.setClave("wrong-password");

        when(authService.authenticate(request)).thenThrow(new RuntimeException("Credenciales inválidas"));

        // Act & Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            authController.authenticate(request);
        });

        assertEquals("Credenciales inválidas", ex.getMessage());
        verify(authService).authenticate(request);
    }
}
