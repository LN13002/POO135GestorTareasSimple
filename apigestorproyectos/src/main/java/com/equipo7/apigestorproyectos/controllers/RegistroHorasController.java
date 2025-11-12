package com.equipo7.apigestorproyectos.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.RegistroHorasCreateDTO;
import com.equipo7.apigestorproyectos.services.RegistroHorasService;

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
@RequestMapping("/api/registrohoras")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Validated
@Tag(name = "Registro de Horas", description = "API para gestión de registros de horas trabajadas en tareas")
public class RegistroHorasController {

    private final RegistroHorasService registroHorasService;

    @GetMapping
    @Operation(summary = "Listar todos los registros de horas", description = "Obtiene una lista completa de todos los registros de horas trabajadas por los empleados en las diferentes tareas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de registros obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroHorasDTO.class)))
    })
    public ResponseEntity<List<RegistroHorasDTO>> listarRegistros() {
        List<RegistroHorasDTO> lista = registroHorasService.listarRegistros();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener registro de horas por ID", description = "Obtiene los detalles completos de un registro de horas específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de horas encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroHorasDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro de horas no encontrado con el ID especificado", content = @Content)
    })
    public ResponseEntity<RegistroHorasDTO> obtenerPorId(
            @Parameter(description = "ID del registro de horas a obtener", required = true, example = "1") @PathVariable Long id) {
        RegistroHorasDTO registro = registroHorasService.obtenerPorId(id);
        return ResponseEntity.ok(registro);
    }

    @PostMapping
    @Operation(summary = "Registrar nuevas horas de trabajo", description = "Crea un nuevo registro de horas trabajadas por un empleado en una tarea específica. El empleado y la tarea deben existir previamente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro de horas creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegistroHorasDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (validaciones fallidas)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Empleado o tarea no encontrados", content = @Content)
    })
    public ResponseEntity<RegistroHorasDTO> guardar(
            @Parameter(description = "Datos del registro de horas a crear (incluye ID de tarea, ID de empleado, horas trabajadas y descripción de la actividad)", required = true) @Valid @RequestBody RegistroHorasCreateDTO dto) {
        RegistroHorasDTO nuevo = registroHorasService.guardarRegistro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de horas", description = "Elimina permanentemente un registro de horas del sistema. Esta acción no se puede deshacer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro de horas eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Registro de horas no encontrado con el ID especificado", content = @Content)
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del registro de horas a eliminar", required = true, example = "1") @PathVariable Long id) {
        registroHorasService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}