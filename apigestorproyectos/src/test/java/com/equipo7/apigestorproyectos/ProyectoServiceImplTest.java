package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.dto.respuesta.ProyectoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.models.EstadoProyecto;
import com.equipo7.apigestorproyectos.models.Proyecto;
import com.equipo7.apigestorproyectos.repository.ProyectoRepository;
import com.equipo7.apigestorproyectos.services.ProyectoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceImplTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private ProyectoServiceImpl service;

    private Proyecto proyecto;
    private ProyectoCreateDTO createDTO;
    private ProyectoUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Test");
        proyecto.setDescripcion("Descripción");
        proyecto.setFechaInicio(LocalDate.of(2024, 1, 1));
        proyecto.setFechaFinEstimada(LocalDate.of(2024, 12, 31));
        proyecto.setPresupuesto(new BigDecimal("100000.00"));
        proyecto.setEstado(EstadoProyecto.PLANIFICACION);

        createDTO = new ProyectoCreateDTO(
                "Nuevo Proyecto",
                "Descripción",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                new BigDecimal("50000.00")
        );

        updateDTO = new ProyectoUpdateDTO(
                "Proyecto Actualizado",
                "Nueva descripción",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                new BigDecimal("75000.00")
        );
    }

    @Test
    void listarTodos_DebeRetornarListaDeProyectos() {
        // Arrange
        when(proyectoRepository.findAll()).thenReturn(Arrays.asList(proyecto));

        // Act
        List<ProyectoResponseDTO> result = service.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Proyecto Test", result.get(0).nombre());
        verify(proyectoRepository).findAll();
    }

    @Test
    void obtenerPorId_DebeRetornarProyecto() {
        // Arrange
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        // Act
        ProyectoResponseDTO result = service.obtenerPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Proyecto Test", result.nombre());
        verify(proyectoRepository).findById(1L);
    }

    @Test
    void obtenerPorId_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(proyectoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.obtenerPorId(999L));
        verify(proyectoRepository).findById(999L);
    }

    @Test
    void crearProyecto_DebeCrearProyecto() {
        // Arrange
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

        // Act
        ProyectoResponseDTO result = service.crearProyecto(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Proyecto Test", result.nombre());
        verify(proyectoRepository).save(any(Proyecto.class));
    }

    @Test
    void crearProyecto_DebeThrowIllegalArgumentException_CuandoFechaFinEsAnterior() {
        // Arrange
        ProyectoCreateDTO dtoInvalido = new ProyectoCreateDTO(
                "Nuevo Proyecto",
                "Descripción",
                LocalDate.of(2024, 12, 31),
                LocalDate.of(2024, 1, 1), // fecha fin antes de inicio
                new BigDecimal("50000.00")
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.crearProyecto(dtoInvalido));
        verify(proyectoRepository, never()).save(any());
    }

    @Test
    void actualizarProyecto_DebeActualizarProyecto() {
        // Arrange
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

        // Act
        ProyectoResponseDTO result = service.actualizarProyecto(1L, updateDTO);

        // Assert
        assertNotNull(result);
        verify(proyectoRepository).findById(1L);
        verify(proyectoRepository).save(any(Proyecto.class));
    }

    @Test
    void actualizarProyecto_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(proyectoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.actualizarProyecto(999L, updateDTO));
        verify(proyectoRepository).findById(999L);
        verify(proyectoRepository, never()).save(any());
    }

    @Test
    void eliminarProyecto_DebeEliminarProyecto() {
        // Arrange
        when(proyectoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(proyectoRepository).deleteById(1L);

        // Act
        service.eliminarProyecto(1L);

        // Assert
        verify(proyectoRepository).existsById(1L);
        verify(proyectoRepository).deleteById(1L);
    }

    @Test
    void eliminarProyecto_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(proyectoRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.eliminarProyecto(999L));
        verify(proyectoRepository).existsById(999L);
        verify(proyectoRepository, never()).deleteById(any());
    }

    @Test
    void obtenerHorasTotales_DebeRetornarHoras() {
        // Arrange
        when(proyectoRepository.existsById(1L)).thenReturn(true);
        when(proyectoRepository.sumarHorasTotalesPorProyecto(1L)).thenReturn(new BigDecimal("250.5"));

        // Act
        BigDecimal result = service.obtenerHorasTotales(1L);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("250.5"), result);
        verify(proyectoRepository).existsById(1L);
        verify(proyectoRepository).sumarHorasTotalesPorProyecto(1L);
    }

    @Test
    void obtenerHorasTotales_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(proyectoRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.obtenerHorasTotales(999L));
        verify(proyectoRepository).existsById(999L);
        verify(proyectoRepository, never()).sumarHorasTotalesPorProyecto(any());
    }

    @Test
    void obtenerProgreso_DebeThrowUnsupportedOperationException() {
        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> service.obtenerProgreso(1L));
    }
}
