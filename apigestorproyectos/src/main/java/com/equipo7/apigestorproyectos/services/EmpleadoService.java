package com.equipo7.apigestorproyectos.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;

public interface EmpleadoService {
    Page<EmpleadoResponseDTO> list(String q, Boolean activo, Pageable pageable);

    EmpleadoResponseDTO getById(Long id);

    EmpleadoResponseDTO create(EmpleadoCreateDTO dto);

    EmpleadoResponseDTO update(Long id, EmpleadoUpdateDTO dto);

    void delete(Long id);
}
