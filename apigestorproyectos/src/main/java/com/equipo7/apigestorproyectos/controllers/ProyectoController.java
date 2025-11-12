package com.equipo7.apigestorproyectos.controllers;

import com.equipo7.apigestorproyectos.dto.respuesta.ProyectoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoUpdateDTO;
import com.equipo7.apigestorproyectos.services.ProyectoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Validated
@Tag(name = "Proyectos", description = "API para gestión de proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping
    @Operation(summary = "Listar todos los proyectos", description = "Obtiene una lista completa de todos los proyectos registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de proyectos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoResponseDTO.class)))
    })
    public ResponseEntity<List<ProyectoResponseDTO>> listarProyectos() {
        return ResponseEntity.ok(proyectoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proyecto por ID", description = "Obtiene los detalles completos de un proyecto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyecto encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado con el ID especificado", content = @Content)
    })
    public ResponseEntity<ProyectoResponseDTO> obtenerProyecto(
            @Parameter(description = "ID del proyecto a obtener", required = true, example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo proyecto", description = "Crea un nuevo proyecto en el sistema con la información proporcionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proyecto creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    public ResponseEntity<ProyectoResponseDTO> crearProyecto(
            @Parameter(description = "Datos del proyecto a crear", required = true) @Valid @RequestBody ProyectoCreateDTO dto) {
        ProyectoResponseDTO nuevoProyecto = proyectoService.crearProyecto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar proyecto existente", description = "Actualiza la información de un proyecto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyecto actualizado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    })
    public ResponseEntity<ProyectoResponseDTO> actualizarProyecto(
            @Parameter(description = "ID del proyecto a actualizar", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Datos actualizados del proyecto", required = true) @Valid @RequestBody ProyectoUpdateDTO dto) {
        return ResponseEntity.ok(proyectoService.actualizarProyecto(id, dto));
    }

    @DeleteMapping("/{id}") // ← CORREGIDO: era "/id" sin las llaves
    @Operation(summary = "Eliminar proyecto", description = "Elimina permanentemente un proyecto del sistema. Esta acción no se puede deshacer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Proyecto eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    })
    public ResponseEntity<Void> eliminarProyecto(
            @Parameter(description = "ID del proyecto a eliminar", required = true, example = "1") @PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/horas-totales")
    @Operation(summary = "Obtener horas totales del proyecto", description = "Calcula y devuelve el total de horas registradas en todas las tareas del proyecto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Horas totales calculadas exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BigDecimal.class))),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    })
    public ResponseEntity<BigDecimal> obtenerHorasTotales(
            @Parameter(description = "ID del proyecto", required = true, example = "1") @PathVariable Long id) {
        BigDecimal horas = proyectoService.obtenerHorasTotales(id);
        return ResponseEntity.ok(horas);
    }

    @GetMapping("/{id}/progreso")
    @Operation(summary = "Obtener progreso del proyecto", description = "Calcula y devuelve el porcentaje de progreso del proyecto basado en las tareas completadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progreso calculado exitosamente (valor entre 0.0 y 100.0)", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Double.class, example = "75.5"))),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    })
    public ResponseEntity<Double> obtenerProgreso(
            @Parameter(description = "ID del proyecto", required = true, example = "1") @PathVariable Long id) {
        Double progreso = proyectoService.obtenerProgreso(id);
        return ResponseEntity.ok(progreso);
    }
}