package com.app.GUZPASEN.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtServiceTest {

    private JwtService jwtService;

    private final String SECRET_KEY = "Zm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFyZm9vYmFy";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        jwtService = new JwtService();
        java.lang.reflect.Field secretKeyField = JwtService.class.getDeclaredField("SECRET_KEY");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtService, SECRET_KEY);
    }

    @Test
    void testGenerateToken_and_getUserName() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        String usernameFromToken = jwtService.getUserName(token);
        assertEquals("testuser", usernameFromToken);
    }

    @Test
    void testGenerateToken_withClaims() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("claimuser");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");

        String token = jwtService.generateToken(claims, userDetails);
        assertNotNull(token);

        String usernameFromToken = jwtService.getUserName(token);
        assertEquals("claimuser", usernameFromToken);

        String role = jwtService.getClaim(token, claimsMap -> claimsMap.get("role", String.class));
        assertEquals("admin", role);
    }

    @Test
    void testValidateToken_valid() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("validuser");

        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_invalidUsername() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user1");

        String token = jwtService.generateToken(userDetails);

        UserDetails differentUser = mock(UserDetails.class);
        when(differentUser.getUsername()).thenReturn("user2");

        boolean isValid = jwtService.validateToken(token, differentUser);

        assertFalse(isValid);
    }

    @Test
    void testValidateToken_expired() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("expireduser");

        Map<String, Object> claims = new HashMap<>();
        String token = io.jsonwebtoken.Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)) // hace 2 días
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)) // hace 1 día (expirado)
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(
                                io.jsonwebtoken.io.Decoders.BASE64.decode(SECRET_KEY)),
                        io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();

        boolean isValid;
        try {
            isValid = jwtService.validateToken(token, userDetails);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            isValid = false;
        }

        assertFalse(isValid);
    }


    @Test
    void testIsTokenExpired() throws Exception {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("anyuser");

        String validToken = jwtService.generateToken(userDetails);
        // isTokenExpired es privado, así que testeamos indirectamente con validateToken

        boolean isValid = jwtService.validateToken(validToken, userDetails);
        assertTrue(isValid); // token válido y no expirado

        // Token expirado lo testeamos arriba
    }
}

