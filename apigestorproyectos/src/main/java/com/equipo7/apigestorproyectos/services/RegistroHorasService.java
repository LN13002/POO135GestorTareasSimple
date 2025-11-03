package com.equipo7.apigestorproyectos.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.models.RegistroHoras;
import com.equipo7.apigestorproyectos.models.Tarea;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;
import com.equipo7.apigestorproyectos.repository.RegistroHorasRepository;
import com.equipo7.apigestorproyectos.repository.TareaRepository;

@Service
public class RegistroHorasService {
    @Autowired
    private RegistroHorasRepository registroHorasRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private TareaRepository tareaRepository;

    // guardar un nuevo registro de horas
    public RegistroHorasDTO guardarRegistro(RegistroHorasDTO dto) {
        RegistroHoras registro = new RegistroHoras();
        registro.setFecha(dto.getFecha());
        registro.setHorasRegistradas(dto.getHorasRegistradas());
        registro.setDescripcionActividad(dto.getDescripcionActividad());
        registro.setFechaRegistro(LocalDateTime.now());

        // Buscar empleado y tarea por ID
        Optional<Empleado> empleado = empleadoRepository.findById(dto.getEmpleadoId());
        Optional<Tarea> tarea = tareaRepository.findById(dto.getTareaId());

        if (empleado.isPresent() && tarea.isPresent()) {
            registro.setEmpleado(empleado.get());
            registro.setTarea(tarea.get());
        } else {
            throw new RuntimeException("Empleado o tarea no encontrados");
        }

        registroHorasRepository.save(registro);
        dto.setId(registro.getId());
        dto.setFechaRegistro(registro.getFechaRegistro());
        return dto;
    }

    // listar todos los registros
    public List<RegistroHorasDTO> listarRegistros() {
        return registroHorasRepository.findAll()
                .stream()
                .map(r -> new RegistroHorasDTO(
                        r.getId(),
                        r.getTarea().getId(),
                        r.getEmpleado().getId(),
                        r.getFecha(),
                        r.getHorasRegistradas(),
                        r.getDescripcionActividad(),
                        r.getFechaRegistro()))
                .collect(Collectors.toList());
    }

    // buscar registro por id
    public RegistroHorasDTO obtenerPorId(Long id) {
        RegistroHoras r = registroHorasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        return new RegistroHorasDTO(
                r.getId(),
                r.getTarea().getId(),
                r.getEmpleado().getId(),
                r.getFecha(),
                r.getHorasRegistradas(),
                r.getDescripcionActividad(),
                r.getFechaRegistro());
    }

    // eliminar registro
    public void eliminarRegistro(Long id) {
        registroHorasRepository.deleteById(id);
    }
}
