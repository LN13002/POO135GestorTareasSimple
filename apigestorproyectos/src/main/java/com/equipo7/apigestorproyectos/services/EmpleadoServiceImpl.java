package com.equipo7.apigestorproyectos.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.mappers.EmpleadoMapper;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repo;
    private final EmpleadoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<EmpleadoResponseDTO> list(String q, Boolean activo, Pageable pageable) {
        Pageable pageableToUse = pageable != null
                ? pageable
                : PageRequest.of(0, 20, Sort.by("id").descending());

        String searchQuery = (q == null || q.isBlank()) ? null : q.trim();

        Page<Empleado> page = repo.search(searchQuery, activo, pageableToUse);
        return page.map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoResponseDTO getById(Long id) {
        Empleado empleado = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));
        return mapper.toResponseDTO(empleado);
    }

    @Override
    public EmpleadoResponseDTO create(EmpleadoCreateDTO dto) {
        // Validar email único
        if (repo.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException(
                    "Ya existe un empleado con el email: " + dto.email());
        }

        // Mapear y guardar
        Empleado empleado = mapper.toEntity(dto);
        Empleado guardado = repo.save(empleado);
        return mapper.toResponseDTO(guardado);
    }

    @Override
    public EmpleadoResponseDTO update(Long id, EmpleadoUpdateDTO dto) {
        // Buscar empleado existente
        Empleado empleado = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));

        // Validar email único si cambió
        if (dto.email() != null &&
                !dto.email().equalsIgnoreCase(empleado.getEmail()) &&
                repo.existsByEmail(dto.email())) {
            throw new DataIntegrityViolationException(
                    "Ya existe un empleado con el email: " + dto.email());
        }

        // Actualizar campos usando MapStruct (solo campos no nulos)
        mapper.updateEntityFromDTO(dto, empleado);

        // Guardar cambios
        Empleado actualizado = repo.save(empleado);
        return mapper.toResponseDTO(actualizado);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado con ID: " + id);
        }
        repo.deleteById(id);
    }
}