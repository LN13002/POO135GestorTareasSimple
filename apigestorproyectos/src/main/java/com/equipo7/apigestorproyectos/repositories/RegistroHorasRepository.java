package com.equipo7.apigestorproyectos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.equipo7.apigestorproyectos.models.RegistroHoras;

@Repository
public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Long> {
    // Metodos personalizados para consultas adicionales pueden ir aquí
}
