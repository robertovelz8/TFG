package com.app.GUZPASEN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.GUZPASEN.models.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long>{

}
