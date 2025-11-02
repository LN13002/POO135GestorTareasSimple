package com.equipo7.apigestorproyectos.repository;

import com.equipo7.apigestorproyectos.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    /*
    * Calcula la suma total de horas trabajadaas en todas las tareas de un proyecto
    * Retorna 0 si no hay registros
    * */
    @Query("""
            SELECT COALESCE(SUM(rh.horas),0)
            FROM proyecto p
            JOIN p.tareas t
            JOIN t.registrosHoras rh
            WHERE p.id = :proyectoId
            """)
    BigDecimal sumarHorasTotalesPorProyecto(Long proyectoId);
}
