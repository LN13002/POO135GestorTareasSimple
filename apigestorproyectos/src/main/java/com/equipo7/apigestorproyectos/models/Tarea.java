package com.equipo7.apigestorproyectos.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "proyecto", "empleadoAsignado", "registrosHoras" })
@EqualsAndHashCode(exclude = { "proyecto", "empleadoAsignado", "registrosHoras" })
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_asignado_id")
    private Empleado empleadoAsignado;

    private LocalDateTime fechaCreacion;

    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroHoras> registrosHoras = new ArrayList<>();
}
