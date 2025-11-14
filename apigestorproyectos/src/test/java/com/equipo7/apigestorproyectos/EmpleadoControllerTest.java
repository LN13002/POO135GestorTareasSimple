package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.controllers.EmpleadoController;
import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.services.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpleadoController.class)
class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpleadoService empleadoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listarEmpleados_DebeRetornarPaginaDeEmpleados() throws Exception {
        // Arrange
        EmpleadoResponseDTO empleado = new EmpleadoResponseDTO(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "Desarrollador",
                LocalDate.of(2024, 1, 1),
                true
        );
        Page<EmpleadoResponseDTO> page = new PageImpl<>(Arrays.asList(empleado));
        when(empleadoService.list(anyString(), any(), any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerEmpleadoPorId_DebeRetornarEmpleado() throws Exception {
        // Arrange
        EmpleadoResponseDTO empleado = new EmpleadoResponseDTO(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "Desarrollador",
                LocalDate.of(2024, 1, 1),
                true
        );
        when(empleadoService.getById(1L)).thenReturn(empleado);

        // Act & Assert
        mockMvc.perform(get("/api/empleados/1"))
                .andExpect(status().isOk());
    }

    @Test
    void crearEmpleado_DebeRetornarEmpleadoCreado() throws Exception {
        // Arrange
        EmpleadoCreateDTO createDTO = new EmpleadoCreateDTO(
                "Juan Pérez",
                "juan@example.com",
                "Desarrollador",
                LocalDate.of(2024, 1, 1),
                true
        );
        EmpleadoResponseDTO responseDTO = new EmpleadoResponseDTO(
                1L,
                "Juan Pérez",
                "juan@example.com",
                "Desarrollador",
                LocalDate.of(2024, 1, 1),
                true
        );
        when(empleadoService.create(any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarEmpleado_DebeRetornarEmpleadoActualizado() throws Exception {
        // Arrange
        EmpleadoUpdateDTO updateDTO = new EmpleadoUpdateDTO(
                "Juan Pérez Actualizado",
                "juan.nuevo@example.com",
                "Senior Developer",
                LocalDate.of(2024, 1, 1),
                true
        );
        EmpleadoResponseDTO responseDTO = new EmpleadoResponseDTO(
                1L,
                "Juan Pérez Actualizado",
                "juan.nuevo@example.com",
                "Senior Developer",
                LocalDate.of(2024, 1, 1),
                true
        );
        when(empleadoService.update(eq(1L), any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/empleados/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarEmpleado_DebeRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(empleadoService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/empleados/1"))
                .andExpect(status().isNoContent());
    }
}
