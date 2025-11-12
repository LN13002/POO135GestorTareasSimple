package com.equipo7.apigestorproyectos.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.services.EmpleadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Validated
@Tag(name = "Empleados", description = "API para gestión de empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    @GetMapping
    @Operation(summary = "Listar empleados", description = "Obtiene una lista paginada de empleados con búsqueda y filtros")
    public ResponseEntity<Page<EmpleadoResponseDTO>> list(
            @Parameter(description = "Término de búsqueda (nombre, email o cargo)") @RequestParam(required = false) String q,

            @Parameter(description = "Filtrar por estado activo/inactivo") @RequestParam(required = false) Boolean activo,

            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<EmpleadoResponseDTO> empleados = service.list(q, activo, pageable);
        return ResponseEntity.ok(empleados);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener empleado", description = "Obtiene un empleado por su ID")
    public ResponseEntity<EmpleadoResponseDTO> get(@PathVariable Long id) {
        EmpleadoResponseDTO empleado = service.getById(id);
        return ResponseEntity.ok(empleado);
    }

    @PostMapping
    @Operation(summary = "Crear empleado", description = "Crea un nuevo empleado")
    public ResponseEntity<EmpleadoResponseDTO> create(@Valid @RequestBody EmpleadoCreateDTO dto) {
        EmpleadoResponseDTO empleado = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar empleado", description = "Actualiza un empleado existente")
    public ResponseEntity<EmpleadoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoUpdateDTO dto) {
        EmpleadoResponseDTO empleado = service.update(id, dto);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar empleado", description = "Elimina un empleado por su ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}