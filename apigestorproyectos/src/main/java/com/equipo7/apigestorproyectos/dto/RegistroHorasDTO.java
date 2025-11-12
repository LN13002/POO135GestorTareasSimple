package com.equipo7.apigestorproyectos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHorasDTO {
    private Long id;
    private Long tareaId;
    private Long empleadoId;
    private LocalDate fecha;
    private BigDecimal horasRegistradas;
    private String descripcionActividad;
    private LocalDateTime fechaRegistro;
}