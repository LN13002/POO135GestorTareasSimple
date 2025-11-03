package com.equipo7.apigestorproyectos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.equipo7.apigestorproyectos.models.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

}
