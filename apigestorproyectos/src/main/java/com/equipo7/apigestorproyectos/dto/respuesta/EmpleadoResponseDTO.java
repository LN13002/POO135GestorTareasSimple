package com.equipo7.apigestorproyectos.dto.respuesta;

import java.time.LocalDate;

public record EmpleadoResponseDTO(Long id,
        String nombre,
        String email,
        String cargo,
        LocalDate fechaContratacion,
        Boolean activo) {

}
