package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.dto.respuesta.TareaResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.mappers.TareaMapper;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.models.EstadoTarea;
import com.equipo7.apigestorproyectos.models.Prioridad;
import com.equipo7.apigestorproyectos.models.Proyecto;
import com.equipo7.apigestorproyectos.models.Tarea;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;
import com.equipo7.apigestorproyectos.repository.ProyectoRepository;
import com.equipo7.apigestorproyectos.repository.TareaRepository;
import com.equipo7.apigestorproyectos.services.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private TareaMapper mapper;

    @InjectMocks
    private TareaService service;

    private Tarea tarea;
    private TareaResponseDTO responseDTO;
    private TareaCreateDTO createDTO;
    private TareaUpdateDTO updateDTO;
    private Proyecto proyecto;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Test");

        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Juan Pérez");

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Tarea Test");
        tarea.setDescripcion("Descripción");
        tarea.setProyecto(proyecto);
        tarea.setEmpleadoAsignado(empleado);
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setFechaVencimiento(LocalDate.now().plusDays(7));
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setPrioridad(Prioridad.ALTA);

        responseDTO = new TareaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Tarea Test");
        responseDTO.setDescripcion("Descripción");
        responseDTO.setProyectoId(1L);
        responseDTO.setProyectoNombre("Proyecto Test");
        responseDTO.setEmpleadoAsignadoId(1L);
        responseDTO.setEmpleadoAsignadoNombre("Juan Pérez");
        responseDTO.setFechaCreacion(LocalDateTime.now());
        responseDTO.setFechaVencimiento(LocalDate.now().plusDays(7));
        responseDTO.setEstado(EstadoTarea.PENDIENTE);
        responseDTO.setPrioridad(Prioridad.ALTA);

        createDTO = new TareaCreateDTO(
                "Nueva Tarea",
                "Descripción",
                1L,
                1L,
                LocalDate.now().plusDays(7),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA
        );

        updateDTO = new TareaUpdateDTO(
                "Tarea Actualizada",
                "Nueva descripción",
                1L,
                LocalDate.now().plusDays(10),
                EstadoTarea.EN_PROGRESO,
                Prioridad.MEDIA
        );
    }

    @Test
    void listarTareas_DebeRetornarListaDeTareas() {
        // Arrange
        when(tareaRepository.findAll()).thenReturn(Arrays.asList(tarea));
        when(mapper.toResponseDTOList(anyList())).thenReturn(Arrays.asList(responseDTO));

        // Act
        List<TareaResponseDTO> result = service.listarTareas();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tareaRepository).findAll();
        verify(mapper).toResponseDTOList(anyList());
    }

    @Test
    void obtenerPorId_DebeRetornarTarea() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(mapper.toResponseDTO(tarea)).thenReturn(responseDTO);

        // Act
        TareaResponseDTO result = service.obtenerPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Tarea Test", result.getNombre());
        verify(tareaRepository).findById(1L);
        verify(mapper).toResponseDTO(tarea);
    }

    @Test
    void obtenerPorId_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(tareaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.obtenerPorId(999L));
        verify(tareaRepository).findById(999L);
    }

    @Test
    void crearTarea_DebeCrearTarea() {
        // Arrange
        when(mapper.toEntity(createDTO)).thenReturn(tarea);
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        when(mapper.toResponseDTO(tarea)).thenReturn(responseDTO);

        // Act
        TareaResponseDTO result = service.crearTarea(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Tarea Test", result.getNombre());
        verify(proyectoRepository).findById(1L);
        verify(empleadoRepository).findById(1L);
        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    void crearTarea_DebeThrowResourceNotFoundException_CuandoProyectoNoExiste() {
        // Arrange
        when(mapper.toEntity(createDTO)).thenReturn(tarea);
        when(proyectoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.crearTarea(createDTO));
        verify(proyectoRepository).findById(1L);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void crearTarea_DebeThrowResourceNotFoundException_CuandoEmpleadoNoExiste() {
        // Arrange
        when(mapper.toEntity(createDTO)).thenReturn(tarea);
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.crearTarea(createDTO));
        verify(proyectoRepository).findById(1L);
        verify(empleadoRepository).findById(1L);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void actualizarTarea_DebeActualizarTarea() {
        // Arrange
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        when(mapper.toResponseDTO(tarea)).thenReturn(responseDTO);
        doNothing().when(mapper).updateEntityFromDTO(updateDTO, tarea);

        // Act
        TareaResponseDTO result = service.actualizarTarea(1L, updateDTO);

        // Assert
        assertNotNull(result);
        verify(tareaRepository).findById(1L);
        verify(tareaRepository).save(any(Tarea.class));
        verify(mapper).updateEntityFromDTO(updateDTO, tarea);
    }

    @Test
    void actualizarTarea_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(tareaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.actualizarTarea(999L, updateDTO));
        verify(tareaRepository).findById(999L);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void eliminarTarea_DebeEliminarTarea() {
        // Arrange
        when(tareaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tareaRepository).deleteById(1L);

        // Act
        service.eliminarTarea(1L);

        // Assert
        verify(tareaRepository).existsById(1L);
        verify(tareaRepository).deleteById(1L);
    }

    @Test
    void eliminarTarea_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(tareaRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.eliminarTarea(999L));
        verify(tareaRepository).existsById(999L);
        verify(tareaRepository, never()).deleteById(any());
    }
}
