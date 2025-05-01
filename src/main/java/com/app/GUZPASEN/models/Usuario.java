package com.app.GUZPASEN.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Usuario implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;
	
	@NotNull
	private String nombre;
	
	@NotNull
	private String apellidos;
	
	@Column (unique = true)
	@NotNull
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	@NotNull
	private String clave;
	
	private boolean usuario_movil;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(rol.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public boolean isAccountNonExpired() {
		return true;
	}
	
	public boolean isAccountNonLocked () {
		return true;
	}
	
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String getPassword() {
		return clave;
	}

	

}
