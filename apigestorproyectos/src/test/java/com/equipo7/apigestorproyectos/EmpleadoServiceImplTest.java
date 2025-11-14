package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.mappers.EmpleadoMapper;
import com.equipo7.apigestorproyectos.models.Empleado;
import com.equipo7.apigestorproyectos.repository.EmpleadoRepository;
import com.equipo7.apigestorproyectos.services.EmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository repo;

    @Mock
    private EmpleadoMapper mapper;

    @InjectMocks
    private EmpleadoServiceImpl service;

    private Empleado empleado;
    private EmpleadoResponseDTO responseDTO;
    private EmpleadoCreateDTO createDTO;
    private EmpleadoUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Juan Pérez");
        empleado.setEmail("juan@example.com");
        empleado.setCargo("Desarrollador");
        empleado.setFechaContratacion(LocalDate.of(2024, 1, 1));
        empleado.setActivo(true);

        responseDTO = new EmpleadoResponseDTO(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "Desarrollador",
                LocalDate.of(2024, 1, 1),
                true
        );

        createDTO = new EmpleadoCreateDTO(
                "Juan Pérez",
                "juan@example.com",
                "Desarrollador",
                LocalDate.of(2024, 1, 1),
                true
        );

        updateDTO = new EmpleadoUpdateDTO(
                "Juan Pérez Actualizado",
                "juan.nuevo@example.com",
                "Senior Developer",
                LocalDate.of(2024, 1, 1),
                true
        );
    }

    @Test
    void list_DebeRetornarPaginaDeEmpleados() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 20);
        Page<Empleado> page = new PageImpl<>(Arrays.asList(empleado));
        when(repo.search(any(), any(), any())).thenReturn(page);
        when(mapper.toResponseDTO(any(Empleado.class))).thenReturn(responseDTO);

        // Act
        Page<EmpleadoResponseDTO> result = service.list(null, null, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repo).search(any(), any(), any());
    }

    @Test
    void getById_DebeRetornarEmpleado() {
        // Arrange
        when(repo.findById(1L)).thenReturn(Optional.of(empleado));
        when(mapper.toResponseDTO(empleado)).thenReturn(responseDTO);

        // Act
        EmpleadoResponseDTO result = service.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Juan Pérez", result.nombre());
        verify(repo).findById(1L);
    }

    @Test
    void getById_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(repo.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getById(999L));
        verify(repo).findById(999L);
    }

    @Test
    void create_DebeCrearEmpleado() {
        // Arrange
        when(repo.existsByEmail("juan@example.com")).thenReturn(false);
        when(mapper.toEntity(createDTO)).thenReturn(empleado);
        when(repo.save(empleado)).thenReturn(empleado);
        when(mapper.toResponseDTO(empleado)).thenReturn(responseDTO);

        // Act
        EmpleadoResponseDTO result = service.create(createDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Juan Pérez", result.nombre());
        verify(repo).existsByEmail("juan@example.com");
        verify(repo).save(empleado);
    }

    @Test
    void create_DebeThrowDataIntegrityViolationException_CuandoEmailYaExiste() {
        // Arrange
        when(repo.existsByEmail("juan@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.create(createDTO));
        verify(repo).existsByEmail("juan@example.com");
        verify(repo, never()).save(any());
    }

    @Test
    void update_DebeActualizarEmpleado() {
        // Arrange
        when(repo.findById(1L)).thenReturn(Optional.of(empleado));
        when(repo.existsByEmail("juan.nuevo@example.com")).thenReturn(false);
        when(repo.save(empleado)).thenReturn(empleado);
        when(mapper.toResponseDTO(empleado)).thenReturn(responseDTO);
        doNothing().when(mapper).updateEntityFromDTO(updateDTO, empleado);

        // Act
        EmpleadoResponseDTO result = service.update(1L, updateDTO);

        // Assert
        assertNotNull(result);
        verify(repo).findById(1L);
        verify(repo).save(empleado);
        verify(mapper).updateEntityFromDTO(updateDTO, empleado);
    }

    @Test
    void update_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(repo.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.update(999L, updateDTO));
        verify(repo).findById(999L);
        verify(repo, never()).save(any());
    }

    @Test
    void update_DebeThrowDataIntegrityViolationException_CuandoEmailYaExiste() {
        // Arrange
        when(repo.findById(1L)).thenReturn(Optional.of(empleado));
        when(repo.existsByEmail("juan.nuevo@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> service.update(1L, updateDTO));
        verify(repo).findById(1L);
        verify(repo).existsByEmail("juan.nuevo@example.com");
        verify(repo, never()).save(any());
    }

    @Test
    void delete_DebeEliminarEmpleado() {
        // Arrange
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);

        // Act
        service.delete(1L);

        // Assert
        verify(repo).existsById(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void delete_DebeThrowResourceNotFoundException_CuandoNoExiste() {
        // Arrange
        when(repo.existsById(999L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete(999L));
        verify(repo).existsById(999L);
        verify(repo, never()).deleteById(any());
    }
}
