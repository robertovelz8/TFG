package com.app.GUZPASEN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.GUZPASEN.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	 Optional<Usuario> findUserByEmail(String email);
	 
	 boolean existsByEmail (String email);
}
