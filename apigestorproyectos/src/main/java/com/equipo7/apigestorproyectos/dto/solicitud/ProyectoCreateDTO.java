package com.equipo7.apigestorproyectos.dto.solicitud;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.LocalDate;

// Definiendo informacion requerida para crear un proyecto
public record ProyectoCreateDTO(
        @NotBlank @Size(max = 255) String nombre,
        @Size(max = 1000) String descripcion,
        @NotNull LocalDate fechaInicio,
        @NotNull LocalDate fechaFinEstimada,

        @NotNull @DecimalMin(
                value = "0.01" ,
                message = "El presupuesto debe ser mayor que 0"
        ) BigDecimal presupuesto

) {
}
