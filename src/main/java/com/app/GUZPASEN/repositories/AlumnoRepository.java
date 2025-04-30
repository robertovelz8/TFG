package com.app.GUZPASEN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.GUZPASEN.models.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String>{

}
