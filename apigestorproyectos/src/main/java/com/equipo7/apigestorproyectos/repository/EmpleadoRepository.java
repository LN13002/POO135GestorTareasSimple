package com.equipo7.apigestorproyectos.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.equipo7.apigestorproyectos.models.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

        boolean existsByEmail(String email);

        Optional<Empleado> findByEmail(String email);

        // Versión nativa para Postgres usando ILIKE (case-insensitive)
        @Query(value = """
                        SELECT e.*
                        FROM empleados e
                        WHERE (:q IS NULL OR
                               e.nombre ILIKE CONCAT('%', :q, '%') OR
                               e.email  ILIKE CONCAT('%', :q, '%') OR
                               e.cargo  ILIKE CONCAT('%', :q, '%'))
                          AND (:activo IS NULL OR e.activo = :activo)
                        """, countQuery = """
                        SELECT COUNT(*)
                        FROM empleados e
                        WHERE (:q IS NULL OR
                               e.nombre ILIKE CONCAT('%', :q, '%') OR
                               e.email  ILIKE CONCAT('%', :q, '%') OR
                               e.cargo  ILIKE CONCAT('%', :q, '%'))
                          AND (:activo IS NULL OR e.activo = :activo)
                        """, nativeQuery = true)
        Page<Empleado> search(@Param("q") String q,
                        @Param("activo") Boolean activo,
                        Pageable pageable);
}
