package com.equipo7.apigestorproyectos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equipo7.apigestorproyectos.dto.respuesta.TareaResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.mappers.TareaMapper;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.models.Proyecto;
import com.equipo7.apigestorproyectos.models.Tarea;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;
import com.equipo7.apigestorproyectos.repository.ProyectoRepository;
import com.equipo7.apigestorproyectos.repository.TareaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final TareaMapper mapper;

    // Listar todas las tareas
    @Transactional(readOnly = true)
    public List<TareaResponseDTO> listarTareas() {
        List<Tarea> tareas = tareaRepository.findAll();
        return mapper.toResponseDTOList(tareas);
    }

    // Obtener tarea por ID
    @Transactional(readOnly = true)
    public TareaResponseDTO obtenerPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));
        return mapper.toResponseDTO(tarea);
    }

    // Crear nueva tarea
    public TareaResponseDTO crearTarea(TareaCreateDTO dto) {
        // Convertir DTO a Entity
        Tarea tarea = mapper.toEntity(dto);

        // Buscar y asignar el proyecto
        Proyecto proyecto = proyectoRepository.findById(dto.proyectoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Proyecto no encontrado con ID: " + dto.proyectoId()));
        tarea.setProyecto(proyecto);

        // Asignar empleado si está presente
        if (dto.empleadoAsignadoId() != null) {
            Empleado empleado = empleadoRepository.findById(dto.empleadoAsignadoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Empleado no encontrado con ID: " + dto.empleadoAsignadoId()));
            tarea.setEmpleadoAsignado(empleado);
        }

        // Guardar
        Tarea guardada = tareaRepository.save(tarea);
        return mapper.toResponseDTO(guardada);
    }

    // Actualizar tarea existente
    public TareaResponseDTO actualizarTarea(Long id, TareaUpdateDTO dto) {
        // Buscar tarea existente
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con ID: " + id));

        // Actualizar campos básicos usando MapStruct
        mapper.updateEntityFromDTO(dto, tarea);

        // Actualizar empleado asignado si cambió
        if (dto.empleadoAsignadoId() != null) {
            Empleado empleado = empleadoRepository.findById(dto.empleadoAsignadoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Empleado no encontrado con ID: " + dto.empleadoAsignadoId()));
            tarea.setEmpleadoAsignado(empleado);
        } else {
            tarea.setEmpleadoAsignado(null);
        }

        // Guardar cambios
        Tarea actualizada = tareaRepository.save(tarea);
        return mapper.toResponseDTO(actualizada);
    }

    // Eliminar tarea
    public void eliminarTarea(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea no encontrada con ID: " + id);
        }
        tareaRepository.deleteById(id);
    }
}