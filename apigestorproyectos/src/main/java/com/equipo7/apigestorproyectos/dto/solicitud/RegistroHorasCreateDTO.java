package com.equipo7.apigestorproyectos.dto.solicitud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroHorasCreateDTO(
        @NotNull(message = "La tarea es obligatoria")
        Long tareaId,
        
        @NotNull(message = "El empleado es obligatorio")
        Long empleadoId,
        
        @NotNull(message = "La fecha es obligatoria") 
        LocalDate fecha,
        
        @NotNull(message = "Las horas registradas son obligatorias")
        @DecimalMin(value = "0.1", message = "Debe registrar al menos 0.1 horas")
        BigDecimal horasRegistradas,
        
        @NotBlank(message = "La descripción de la actividad es obligatoria")
        String descripcionActividad,
        
        @NotNull(message = "La fecha de registro es obligatoria")
        LocalDateTime fechaRegistro
) {}