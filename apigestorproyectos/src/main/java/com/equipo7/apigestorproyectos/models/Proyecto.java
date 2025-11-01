package com.equipo7.apigestorproyectos.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1000) // descripción más larga
    private String descripcion;

    private LocalDate fechaInicio;

    private LocalDate fechaFinEstimada;

    @Enumerated(EnumType.STRING)
    private EstadoProyecto estado;

    private BigDecimal presupuesto;

    // Relacion con tareas (necesaria para JPQL y el diseño relacional)
    // Un proyecto tiene muchas tareas
    @OneToMany(mappedBy = "proyecto",
            cascade = CascadeType.ALL,
            orphanRemoval = true, // Al quitar una tarea de la lista se elimina de la BD
            fetch = FetchType.LAZY)
    private List<Tarea> tareas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinEstimada() {
        return fechaFinEstimada;
    }

    public void setFechaFinEstimada(LocalDate fechaFinEstimada) {
        this.fechaFinEstimada = fechaFinEstimada;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProyecto estado) {
        this.estado = estado;
    }

    public BigDecimal getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(BigDecimal presupuesto) {
        this.presupuesto = presupuesto;
    }
}