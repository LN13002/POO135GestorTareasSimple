package com.equipo7.apigestorproyectos.dto.solicitud;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProyectoUpdateDTO(
        @Size(max = 255) String nombre,
        @Size(max = 1000) String descripcion,
        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,
        @DecimalMin(
                value = "0.01",
                message = "El presupuesto debe ser mayor que 0"
        ) BigDecimal presupuesto
) {
}
