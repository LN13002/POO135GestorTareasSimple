package com.equipo7.apigestorproyectos.dto.respuesta;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.equipo7.apigestorproyectos.models.EstadoTarea;
import com.equipo7.apigestorproyectos.models.Prioridad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareaResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;

    private Long proyectoId;
    private String proyectoNombre;

    private Long empleadoAsignadoId;
    private String empleadoAsignadoNombre;

    private LocalDateTime fechaCreacion;
    private LocalDate fechaVencimiento;

    private EstadoTarea estado;
    private Prioridad prioridad;
}