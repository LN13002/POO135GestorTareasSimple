package com.equipo7.apigestorproyectos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistroHorasDTO {
    private Long id;
    private Long tareaId;
    private Long empleadoId;
    private LocalDate fecha;
    private BigDecimal horasRegistradas;
    private String descripcionActividad;
    private LocalDateTime fechaRegistro;

    // Constructores
    public RegistroHorasDTO() {
    }

    public RegistroHorasDTO(Long id, Long tareaId, Long empleadoId, LocalDate fecha, BigDecimal horasRegistradas,
            String descripcionActividad, LocalDateTime fechaRegistro) {
        this.id = id;
        this.tareaId = tareaId;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.horasRegistradas = horasRegistradas;
        this.descripcionActividad = descripcionActividad;
        this.fechaRegistro = fechaRegistro;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getHorasRegistradas() {
        return horasRegistradas;
    }

    public void setHorasRegistradas(BigDecimal horasRegistradas) {
        this.horasRegistradas = horasRegistradas;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
