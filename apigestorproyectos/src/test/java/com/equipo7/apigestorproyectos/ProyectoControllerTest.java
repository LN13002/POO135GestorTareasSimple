package com.equipo7.apigestorproyectos;
import com.equipo7.apigestorproyectos.controllers.ProyectoController;
import com.equipo7.apigestorproyectos.dto.respuesta.ProyectoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.ProyectoUpdateDTO;
import com.equipo7.apigestorproyectos.models.EstadoProyecto;
import com.equipo7.apigestorproyectos.services.ProyectoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProyectoController.class)
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProyectoService proyectoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listarProyectos_DebeRetornarListaDeProyectos() throws Exception {
        // Arrange
        ProyectoResponseDTO proyecto = new ProyectoResponseDTO(
                1L, "Proyecto 1", "Desc", LocalDate.now(),
                LocalDate.now().plusMonths(6), EstadoProyecto.EN_PROGRESO,
                new BigDecimal("10000")
        );
        when(proyectoService.listarTodos()).thenReturn(Arrays.asList(proyecto));

        // Act & Assert
        mockMvc.perform(get("/api/proyectos"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProyectoPorId_DebeRetornarProyecto() throws Exception {
        // Arrange
        ProyectoResponseDTO proyecto = new ProyectoResponseDTO(
                1L, "Proyecto 1", "Desc", LocalDate.now(),
                LocalDate.now().plusMonths(6), EstadoProyecto.EN_PROGRESO,
                new BigDecimal("10000")
        );
        when(proyectoService.obtenerPorId(1L)).thenReturn(proyecto);

        // Act & Assert
        mockMvc.perform(get("/api/proyectos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void crearProyecto_DebeRetornarProyectoCreado() throws Exception {
        // Arrange
        ProyectoCreateDTO createDTO = new ProyectoCreateDTO(
                "Nuevo Proyecto", "Descripción", LocalDate.now(),
                LocalDate.now().plusMonths(6), new BigDecimal("5000")
        );
        ProyectoResponseDTO responseDTO = new ProyectoResponseDTO(
                1L, "Nuevo Proyecto", "Descripción", LocalDate.now(),
                LocalDate.now().plusMonths(6), EstadoProyecto.PLANIFICACION,
                new BigDecimal("5000")
        );
        when(proyectoService.crearProyecto(any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarProyecto_DebeRetornarProyectoActualizado() throws Exception {
        // Arrange
        ProyectoUpdateDTO updateDTO = new ProyectoUpdateDTO(
                "Proyecto Actualizado", "Nueva desc", LocalDate.now(),
                LocalDate.now().plusMonths(6), new BigDecimal("7500")
        );
        ProyectoResponseDTO responseDTO = new ProyectoResponseDTO(
                1L, "Proyecto Actualizado", "Nueva desc", LocalDate.now(),
                LocalDate.now().plusMonths(6), EstadoProyecto.EN_PROGRESO,
                new BigDecimal("7500")
        );
        when(proyectoService.actualizarProyecto(eq(1L), any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/proyectos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarProyecto_DebeRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(proyectoService).eliminarProyecto(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/proyectos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerHorasTotales_DebeRetornarHoras() throws Exception {
        // Arrange
        when(proyectoService.obtenerHorasTotales(1L)).thenReturn(new BigDecimal("120.5"));

        // Act & Assert
        mockMvc.perform(get("/api/proyectos/1/horas-totales"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProgreso_DebeRetornarPorcentaje() throws Exception {
        // Arrange
        when(proyectoService.obtenerProgreso(1L)).thenReturn(75.0);

        // Act & Assert
        mockMvc.perform(get("/api/proyectos/1/progreso"))
                .andExpect(status().isOk());
    }
}