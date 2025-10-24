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

    @Query("""
            SELECT e FROM Empleado e
            WHERE (:q IS NULL OR
                   LOWER(e.nombre) LIKE LOWER(CONCAT('%', :q, '%')) OR
                   LOWER(e.email)  LIKE LOWER(CONCAT('%', :q, '%')) OR
                   LOWER(e.cargo)  LIKE LOWER(CONCAT('%', :q, '%')))
              AND (:activo IS NULL OR e.activo = :activo)
            """)
    Page<Empleado> search(@Param("q") String q,
            @Param("activo") Boolean activo,
            Pageable pageable);
}
