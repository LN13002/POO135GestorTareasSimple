package com.equipo7.apigestorproyectos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.equipo7.apigestorproyectos.dto.respuesta.TareaResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaUpdateDTO;
import com.equipo7.apigestorproyectos.services.TareaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Validated
@Tag(name = "Tareas", description = "API para gestión de tareas del proyecto")
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    @Operation(summary = "Listar todas las tareas", description = "Obtiene una lista completa de todas las tareas con información detallada del proyecto y empleado asignado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponseDTO.class)))
    })
    public ResponseEntity<List<TareaResponseDTO>> listarTareas() {
        List<TareaResponseDTO> tareas = tareaService.listarTareas();
        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tarea por ID", description = "Obtiene los detalles completos de una tarea específica incluyendo información del proyecto y empleado asignado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea encontrada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada con el ID especificado", content = @Content)
    })
    public ResponseEntity<TareaResponseDTO> obtenerTarea(
            @Parameter(description = "ID de la tarea a obtener", required = true, example = "1") @PathVariable Long id) {
        TareaResponseDTO tarea = tareaService.obtenerPorId(id);
        return ResponseEntity.ok(tarea);
    }

    @PostMapping
    @Operation(summary = "Crear nueva tarea", description = "Crea una nueva tarea asignándola a un proyecto y opcionalmente a un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Proyecto o empleado no encontrado", content = @Content)
    })
    public ResponseEntity<TareaResponseDTO> crearTarea(
            @Parameter(description = "Datos de la tarea a crear", required = true) @Valid @RequestBody TareaCreateDTO dto) {
        TareaResponseDTO nuevaTarea = tareaService.crearTarea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea existente", description = "Actualiza los datos de una tarea existente. Permite cambiar el empleado asignado, estado, prioridad y otros campos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tarea o empleado no encontrado", content = @Content)
    })
    public ResponseEntity<TareaResponseDTO> actualizarTarea(
            @Parameter(description = "ID de la tarea a actualizar", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la tarea", required = true) @Valid @RequestBody TareaUpdateDTO dto) {
        TareaResponseDTO tareaActualizada = tareaService.actualizarTarea(id, dto);
        return ResponseEntity.ok(tareaActualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina permanentemente una tarea del sistema. Esta acción no se puede deshacer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada con el ID especificado", content = @Content)
    })
    public ResponseEntity<Void> eliminarTarea(
            @Parameter(description = "ID de la tarea a eliminar", required = true, example = "1") @PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }
}