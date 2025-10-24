package com.equipo7.apigestorproyectos.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repo;

    @Override
    public Page<EmpleadoResponseDTO> list(String q, Boolean activo, Pageable pageable) {
        Page<Empleado> page = repo.search(
                (q == null || q.isBlank()) ? null : q.trim(),
                activo,
                pageable == null ? PageRequest.of(0, 20, Sort.by("id").descending()) : pageable);
        return page.map(this::toDto);
    }

    @Override
    public EmpleadoResponseDTO getById(Long id) {
        Empleado e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado " + id + " no encontrado"));
        return toDto(e);
    }

    @Override
    public EmpleadoResponseDTO create(EmpleadoCreateDTO dto) {
        if (repo.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException("Ya existe un empleado con email " + dto.email());
        }
        Empleado e = new Empleado();
        e.setNombre(dto.nombre());
        e.setEmail(dto.email());
        e.setCargo(dto.cargo());
        e.setFechaContratacion(dto.fechaContratacion());
        e.setActivo(dto.activo());
        return toDto(repo.save(e));
    }

    @Override
    public EmpleadoResponseDTO update(Long id, EmpleadoUpdateDTO dto) {
        Empleado e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado " + id + " no encontrado"));

        if (dto.email() != null && !dto.email().equalsIgnoreCase(e.getEmail())
                && repo.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException("Ya existe un empleado con email " + dto.email());
        }

        if (dto.nombre() != null)
            e.setNombre(dto.nombre());
        if (dto.email() != null)
            e.setEmail(dto.email());
        if (dto.cargo() != null)
            e.setCargo(dto.cargo());
        if (dto.fechaContratacion() != null)
            e.setFechaContratacion(dto.fechaContratacion());
        if (dto.activo() != null)
            e.setActivo(dto.activo());

        return toDto(repo.save(e));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Empleado " + id + " no encontrado");
        }
        repo.deleteById(id);
    }

    private EmpleadoResponseDTO toDto(Empleado e) {
        return new EmpleadoResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getEmail(),
                e.getCargo(),
                e.getFechaContratacion(),
                e.getActivo());
    }

}
