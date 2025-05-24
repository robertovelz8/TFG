package com.app.GUZPASEN.config;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JwtFilterTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Limpiar contexto antes de cada test
    }

    @Test
    void testFilter_skipsIfNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void testFilter_skipsIfHeaderDoesNotStartWithBearer() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Basic xyz");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void testFilter_setsAuthenticationIfValidToken() throws ServletException, IOException {
        String jwtToken = "valid.jwt.token";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.getUserName(jwtToken)).thenReturn(username);
        SecurityContextHolder.getContext().setAuthentication(null);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(jwtToken, userDetails)).thenReturn(true);
        when(userDetails.getAuthorities()).thenReturn(List.of());

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).getUserName(jwtToken);
        verify(userDetailsService).loadUserByUsername(username);
        verify(jwtService).validateToken(jwtToken, userDetails);
        verify(filterChain).doFilter(request, response);

        // Confirmar que el usuario se autentica en el contexto
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        assert authentication.getPrincipal().equals(userDetails);
    }


    @Test
    void testFilter_doesNotSetAuthenticationIfTokenInvalid() throws ServletException, IOException {
        String jwtToken = "invalid.jwt.token";
        String username = "testuser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.getUserName(jwtToken)).thenReturn(username);

        SecurityContextHolder.getContext().setAuthentication(null);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(jwtToken, userDetails)).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(jwtService).getUserName(jwtToken);
        verify(jwtService).validateToken(jwtToken, userDetails);
        verify(filterChain).doFilter(request, response);
    }


    @Test
    void testFilter_skipsIfAlreadyAuthenticated() throws ServletException, IOException {
        String jwtToken = "some.token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtService.getUserName(jwtToken)).thenReturn("user@example.com");

        // Simular autenticación ya existente
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("user", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtService).getUserName(jwtToken);
        verifyNoMoreInteractions(jwtService, userDetailsService);
    }
}

