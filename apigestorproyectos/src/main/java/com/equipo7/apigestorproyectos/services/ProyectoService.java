package com.equipo7.apigestorproyectos.services;

import com.equipo7.apigestorproyectos.dto.respuesta.ProyectoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoUpdateDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProyectoService {
    // Metodos utiles para implementar la logica del negocio

    List<ProyectoResponseDTO> listarTodos();

    ProyectoResponseDTO obtenerPorId(Long id);

    ProyectoResponseDTO crearProyecto(ProyectoCreateDTO dto);

    ProyectoResponseDTO actualizarProyecto(Long id, ProyectoUpdateDTO dto);

    void eliminarProyecto(Long id);

    BigDecimal obtenerHorasTotales(Long id);

    Double obtenerProgreso(Long id);

}
