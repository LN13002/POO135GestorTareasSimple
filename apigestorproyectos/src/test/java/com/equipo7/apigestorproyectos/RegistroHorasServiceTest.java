package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.RegistroHorasCreateDTO;
import com.equipo7.apigestorproyectos.mappers.RegistroHorasMapper;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.models.RegistroHoras;
import com.equipo7.apigestorproyectos.models.Tarea;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;
import com.equipo7.apigestorproyectos.repository.RegistroHorasRepository;
import com.equipo7.apigestorproyectos.repository.TareaRepository;
import com.equipo7.apigestorproyectos.services.RegistroHorasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroHorasServiceTest {

    @Mock
    private RegistroHorasRepository registroHorasRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private RegistroHorasMapper mapper;

    @InjectMocks
    private RegistroHorasService service;

    private RegistroHoras registroHoras;
    private RegistroHorasDTO registroHorasDTO;
    private RegistroHorasCreateDTO createDTO;
    private Empleado empleado;
    private Tarea tarea;

    @BeforeEach
    void setUp() {
        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Juan Pérez");

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Tarea Test");

        // Usar los nombres correctos del modelo actualizado
        registroHoras = new RegistroHoras();
        registroHoras.setId(1L);
        registroHoras.setEmpleado(empleado);
        registroHoras.setTarea(tarea);
        registroHoras.setFecha(LocalDate.now());
        registroHoras.setHorasTrabajadas(8.0); // Double, no BigDecimal
        registroHoras.setDescripcion("Desarrollo"); // descripcion, no descripcionActividad
        registroHoras.setFechaRegistro(LocalDateTime.now());

        registroHorasDTO = new RegistroHorasDTO();
        registroHorasDTO.setId(1L);
        registroHorasDTO.setTareaId(1L);
        registroHorasDTO.setEmpleadoId(1L);
        registroHorasDTO.setFecha(LocalDate.now());
        registroHorasDTO.setHorasRegistradas(new BigDecimal("8.0"));
        registroHorasDTO.setDescripcionActividad("Desarrollo");
        registroHorasDTO.setFechaRegistro(LocalDateTime.now());

        createDTO = new RegistroHorasCreateDTO(
                1L,
                1L,
                LocalDate.now(),
                new BigDecimal("8.0"),
                "Desarrollo",
                LocalDateTime.now()
        );
    }

    @Test
    void guardarRegistro_DebeCrearRegistro() {
        // Arrange
        when(mapper.toEntity(createDTO)).thenReturn(registroHoras);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(registroHorasRepository.save(any(RegistroHoras.class))).thenReturn(registroHoras);
        when(mapper.toDTO(registroHoras)).thenReturn(registroHorasDTO);

        // Act
        RegistroHorasDTO result = service.guardarRegistro(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(empleadoRepository).findById(1L);
        verify(tareaRepository).findById(1L);
        verify(registroHorasRepository).save(any(RegistroHoras.class));
    }

    @Test
    void guardarRegistro_DebeThrowRuntimeException_CuandoEmpleadoNoExiste() {
        // Arrange
        when(mapper.toEntity(createDTO)).thenReturn(registroHoras);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.guardarRegistro(createDTO));
        verify(empleadoRepository).findById(1L);
        verify(registroHorasRepository, never()).save(any());
    }

    @Test
    void guardarRegistro_DebeThrowRuntimeException_CuandoTareaNoExiste() {
        // Arrange
        when(mapper.toEntity(createDTO)).thenReturn(registroHoras);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(tareaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.guardarRegistro(createDTO));
        verify(empleadoRepository).findById(1L);
        verify(tareaRepository).findById(1L);
        verify(registroHorasRepository, never()).save(any());
    }

    @Test
    void listarRegistros_DebeRetornarListaDeRegistros() {
        // Arrange
        when(registroHorasRepository.findAll()).thenReturn(Arrays.asList(registroHoras));
        when(mapper.toDTOList(anyList())).thenReturn(Arrays.asList(registroHorasDTO));

        // Act
        List<RegistroHorasDTO> result = service.listarRegistros();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(registroHorasRepository).findAll();
        verify(mapper).toDTOList(anyList());
    }

    @Test
    void obtenerPorId_DebeRetornarRegistro() {
        // Arrange
        when(registroHorasRepository.findById(1L)).thenReturn(Optional.of(registroHoras));
        when(mapper.toDTO(registroHoras)).thenReturn(registroHorasDTO);

        // Act
        RegistroHorasDTO result = service.obtenerPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(registroHorasRepository).findById(1L);
        verify(mapper).toDTO(registroHoras);
    }

    @Test
    void obtenerPorId_DebeThrowRuntimeException_CuandoNoExiste() {
        // Arrange
        when(registroHorasRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.obtenerPorId(999L));
        verify(registroHorasRepository).findById(999L);
    }

    @Test
    void eliminarRegistro_DebeEliminarRegistro() {
        // Arrange
        when(registroHorasRepository.existsById(1L)).thenReturn(true);
        doNothing().when(registroHorasRepository).deleteById(1L);

        // Act
        service.eliminarRegistro(1L);

        // Assert
        verify(registroHorasRepository).existsById(1L);
        verify(registroHorasRepository).deleteById(1L);
    }

    @Test
    void eliminarRegistro_DebeThrowRuntimeException_CuandoNoExiste() {
        // Arrange
        when(registroHorasRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.eliminarRegistro(999L));
        verify(registroHorasRepository).existsById(999L);
        verify(registroHorasRepository, never()).deleteById(any());
    }
}
