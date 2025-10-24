package com.equipo7.apigestorproyectos.dto.solicitud;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record EmpleadoUpdateDTO(@Size(max = 255) String nombre,
        @Email @Size(max = 255) String email,
        @Size(max = 255) String cargo,
        LocalDate fechaContratacion,
        Boolean activo) {
}
