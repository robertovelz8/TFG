package com.app.GUZPASEN.models;

import com.app.GUZPASEN.models.Rol;
import com.app.GUZPASEN.models.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    void testGetAuthorities_returnsCorrectAuthority() {
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .clave("password123")
                .rol(Rol.PROFESOR)
                .build();

        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("PROFESOR")));
    }

    @Test
    void testGetUsername_returnsEmail() {
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .build();

        assertEquals("test@example.com", usuario.getUsername());
    }

    @Test
    void testGetPassword_returnsClave() {
        Usuario usuario = Usuario.builder()
                .clave("securePassword")
                .build();

        assertEquals("securePassword", usuario.getPassword());
    }

    @Test
    void testAccountNonExpired_returnsTrue() {
        Usuario usuario = new Usuario();
        assertTrue(usuario.isAccountNonExpired());
    }

    @Test
    void testAccountNonLocked_returnsTrue() {
        Usuario usuario = new Usuario();
        assertTrue(usuario.isAccountNonLocked());
    }

    @Test
    void testCredentialsNonExpired_returnsTrue() {
        Usuario usuario = new Usuario();
        assertTrue(usuario.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled_returnsTrue() {
        Usuario usuario = new Usuario();
        assertTrue(usuario.isEnabled());
    }
}

