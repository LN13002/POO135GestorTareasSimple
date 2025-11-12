package com.equipo7.apigestorproyectos.dto.respuesta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record RegistroHorasResponseDTO(
        @NotBlank Long tareaId,
        @NotBlank Long empleadoId,
        @NotBlank LocalDate fecha,
        @NotBlank BigDecimal horasRegistradas,
        @NotBlank String descripcionActividad,
        @NotBlank LocalDateTime fechaRegistro) {
}
