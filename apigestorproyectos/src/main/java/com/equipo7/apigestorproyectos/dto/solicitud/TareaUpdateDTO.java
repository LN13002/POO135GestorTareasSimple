package com.equipo7.apigestorproyectos.dto.solicitud;

import java.time.LocalDate;

import com.equipo7.apigestorproyectos.models.EstadoTarea;
import com.equipo7.apigestorproyectos.models.Prioridad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TareaUpdateDTO(
        @NotBlank(message = "El nombre de la tarea es obligatorio") @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres") String nombre,

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres") String descripcion,

        Long empleadoAsignadoId,

        LocalDate fechaVencimiento,

        @NotNull(message = "El estado es obligatorio") EstadoTarea estado,

        @NotNull(message = "La prioridad es obligatoria") Prioridad prioridad) {
}