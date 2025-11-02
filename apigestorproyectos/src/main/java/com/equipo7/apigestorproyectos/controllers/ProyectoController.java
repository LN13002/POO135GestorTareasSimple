// src/main/java/com/equipo7/apigestorproyectos/controllers/ProyectoController.java
package com.equipo7.apigestorproyectos.controllers;

import com.equipo7.apigestorproyectos.dto.respuesta.ProyectoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoUpdateDTO;
import com.equipo7.apigestorproyectos.services.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    // Inyectamos la INTERFAZ (buena práctica)
    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public ResponseEntity<List<ProyectoResponseDTO>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> obtenerProyecto(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProyectoResponseDTO> crearProyecto(@Valid @RequestBody ProyectoCreateDTO dto) {
        return ResponseEntity.ok(proyectoService.crearProyecto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoResponseDTO> actualizarProyecto(
            @PathVariable Long id,
            @Valid @RequestBody ProyectoUpdateDTO dto) {
        return ResponseEntity.ok(proyectoService.actualizarProyecto(id, dto));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build(); // Muestra el codigo 204 -> No content
    }

    // Endpoints adicionales
    @GetMapping("/{id}/horas-totales")
    public ResponseEntity<BigDecimal> obtenerHorasTotales(@PathVariable Long id) {
        BigDecimal horas = proyectoService.obtenerHorasTotales(id);
        return ResponseEntity.ok(horas);
    }

    @GetMapping("/{id}/progreso")
    public ResponseEntity<Double> obtenerProgreso(@PathVariable Long id) {
        Double progreso = proyectoService.obtenerProgreso(id);
        return ResponseEntity.ok(progreso);
    }

}