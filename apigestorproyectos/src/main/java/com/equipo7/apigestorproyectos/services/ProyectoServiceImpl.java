// src/main/java/com/equipo7/apigestorproyectos/services/ProyectoServiceImpl.java
package com.equipo7.apigestorproyectos.services;

import com.equipo7.apigestorproyectos.dto.respuesta.ProyectoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.models.EstadoProyecto;
import com.equipo7.apigestorproyectos.models.Proyecto;
import com.equipo7.apigestorproyectos.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    // Llamando al repositorio
    private final ProyectoRepository proyectoRepository;

    // Inyectamos la dependencia del repositorio por medio de constructor
    public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public List<ProyectoResponseDTO> listarTodos() {
        return proyectoRepository.findAll().stream()
                .map(this::convertirAProyectoResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProyectoResponseDTO obtenerPorId(Long id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));
        return convertirAProyectoResponseDTO(proyecto);
    }

    @Override
    public ProyectoResponseDTO crearProyecto(ProyectoCreateDTO dto) {
        // Validación de fechas
        if (!dto.fechaFinEstimada().isAfter(dto.fechaInicio()) && !dto.fechaFinEstimada().equals(dto.fechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin estimada debe ser igual o posterior a la fecha de inicio");
        }

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.nombre());
        proyecto.setDescripcion(dto.descripcion());
        proyecto.setFechaInicio(dto.fechaInicio());
        proyecto.setFechaFinEstimada(dto.fechaFinEstimada());
        proyecto.setPresupuesto(dto.presupuesto());
        proyecto.setEstado(EstadoProyecto.PLANIFICACION); // ✅ Según CU05

        Proyecto guardado = proyectoRepository.save(proyecto);
        return convertirAProyectoResponseDTO(guardado);
    }

    @Override
    public ProyectoResponseDTO actualizarProyecto(Long id, ProyectoUpdateDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con ID: " + id));

        if (dto.nombre() != null) proyecto.setNombre(dto.nombre());
        if (dto.descripcion() != null) proyecto.setDescripcion(dto.descripcion());
        if (dto.fechaInicio() != null) proyecto.setFechaInicio(dto.fechaInicio());
        if (dto.fechaFinEstimada() != null) {
            // Validar fechas al actualizar
            if (!dto.fechaFinEstimada().isAfter(proyecto.getFechaInicio()) && !dto.fechaFinEstimada().equals(proyecto.getFechaInicio())) {
                throw new IllegalArgumentException("La fecha de fin estimada debe ser igual o posterior a la fecha de inicio");
            }
            proyecto.setFechaFinEstimada(dto.fechaFinEstimada());
        }
        if (dto.presupuesto() != null) proyecto.setPresupuesto(dto.presupuesto());

        Proyecto actualizado = proyectoRepository.save(proyecto);
        return convertirAProyectoResponseDTO(actualizado);
    }

    @Override
    public void eliminarProyecto(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyecto no encontrado con ID: " + id);
        }
        proyectoRepository.deleteById(id);
    }

    @Override
    public BigDecimal obtenerHorasTotales(Long id) {
        // Verificar que el proyecto exista
        if (!proyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyecto no encontrado con ID: " + id);
        }
        return proyectoRepository.sumarHorasTotalesPorProyecto(id);
    }

    @Override
    public Double obtenerProgreso(Long id) {
        // Pendiente: basado en tareas completadas vs totales
        throw new UnsupportedOperationException("No implementado aún");
    }

    private ProyectoResponseDTO convertirAProyectoResponseDTO(Proyecto proyecto) {
        return new ProyectoResponseDTO(
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getFechaInicio(),
                proyecto.getFechaFinEstimada(),
                proyecto.getEstado(),
                proyecto.getPresupuesto()
        );
    }
}