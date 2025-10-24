package com.equipo7.apigestorproyectos.dto.solicitud;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmpleadoCreateDTO(
        @NotBlank @Size(max = 255) String nombre,
        @NotBlank @Email @Size(max = 255) String email,
        @Size(max = 255) String cargo,
        @NotNull LocalDate fechaContratacion,
        @NotNull Boolean activo) {
}