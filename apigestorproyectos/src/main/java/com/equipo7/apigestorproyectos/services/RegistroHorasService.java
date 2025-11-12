package com.equipo7.apigestorproyectos.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.RegistroHorasCreateDTO;
import com.equipo7.apigestorproyectos.mappers.RegistroHorasMapper;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.models.RegistroHoras;
import com.equipo7.apigestorproyectos.models.Tarea;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;
import com.equipo7.apigestorproyectos.repository.RegistroHorasRepository;
import com.equipo7.apigestorproyectos.repository.TareaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistroHorasService {

    private final RegistroHorasRepository registroHorasRepository;
    private final EmpleadoRepository empleadoRepository;
    private final TareaRepository tareaRepository;
    private final RegistroHorasMapper mapper;

    // Guardar un nuevo registro de horas
    public RegistroHorasDTO guardarRegistro(RegistroHorasCreateDTO dto) {
        RegistroHoras registro = mapper.toEntity(dto);

        Empleado empleado = empleadoRepository.findById(dto.empleadoId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + dto.empleadoId()));

        Tarea tarea = tareaRepository.findById(dto.tareaId())
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + dto.tareaId()));

        registro.setEmpleado(empleado);
        registro.setTarea(tarea);

        RegistroHoras guardado = registroHorasRepository.save(registro);

        return mapper.toDTO(guardado);
    }

    // Listar todos los registros
    @Transactional(readOnly = true)
    public List<RegistroHorasDTO> listarRegistros() {
        List<RegistroHoras> registros = registroHorasRepository.findAll();
        return mapper.toDTOList(registros);
    }

    // Buscar registro por ID
    @Transactional(readOnly = true)
    public RegistroHorasDTO obtenerPorId(Long id) {
        RegistroHoras registro = registroHorasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));

        return mapper.toDTO(registro);
    }

    // Eliminar registro
    public void eliminarRegistro(Long id) {
        if (!registroHorasRepository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado con ID: " + id);
        }
        registroHorasRepository.deleteById(id);
    }
}