package com.app.GUZPASEN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.GUZPASEN.models.Sancion;

@Repository
public interface SancionRepository extends JpaRepository<Sancion, Long>{

}
