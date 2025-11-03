package com.equipo7.apigestorproyectos.dto.respuesta;

import com.equipo7.apigestorproyectos.models.EstadoProyecto;

import java.math.BigDecimal;
import java.time.LocalDate;

// Mostramos todos los datos del proyecto
public record ProyectoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,
        EstadoProyecto estado,
        BigDecimal presupuesto
) {
}
