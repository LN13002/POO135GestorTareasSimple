package com.equipo7.apigestorproyectos.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.services.EmpleadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
@Validated
public class EmpleadoController {
    private final EmpleadoService service = null;

    @GetMapping
    public Page<EmpleadoResponseDTO> list(@RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean activo,
            Pageable pageable) {
        return service.list(q, activo, pageable);
    }

    @GetMapping("/{id}")
    public EmpleadoResponseDTO get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoResponseDTO create(@Valid @RequestBody EmpleadoCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public EmpleadoResponseDTO update(@PathVariable Long id, @Valid @RequestBody EmpleadoUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
