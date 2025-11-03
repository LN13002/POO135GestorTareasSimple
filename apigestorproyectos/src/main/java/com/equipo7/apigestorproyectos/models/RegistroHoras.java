package com.equipo7.apigestorproyectos.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "registro_horas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "tarea", "empleado" })
@EqualsAndHashCode(exclude = { "tarea", "empleado" })
public class RegistroHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    private LocalDate fecha;

    private BigDecimal horasRegistradas;

    @Column(length = 1000)
    private String descripcionActividad;

    private LocalDateTime fechaRegistro;
}
