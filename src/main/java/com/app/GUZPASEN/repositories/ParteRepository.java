package com.app.GUZPASEN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.GUZPASEN.models.Parte;

@Repository
public interface ParteRepository extends JpaRepository<Parte, Long>{
	
}
