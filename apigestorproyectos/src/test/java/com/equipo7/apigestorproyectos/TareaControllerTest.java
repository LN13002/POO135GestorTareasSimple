package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.controllers.TareaController;
import com.equipo7.apigestorproyectos.dto.respuesta.TareaResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaUpdateDTO;
import com.equipo7.apigestorproyectos.models.EstadoTarea;
import com.equipo7.apigestorproyectos.models.Prioridad;
import com.equipo7.apigestorproyectos.services.TareaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TareaService tareaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listarTareas_DebeRetornarListaDeTareas() throws Exception {
        // Arrange
        TareaResponseDTO tarea = new TareaResponseDTO();
        tarea.setId(1L);
        tarea.setNombre("Tarea 1");
        tarea.setDescripcion("Descripción");
        tarea.setProyectoId(1L);
        tarea.setProyectoNombre("Proyecto 1");
        tarea.setEmpleadoAsignadoId(1L);
        tarea.setEmpleadoAsignadoNombre("Juan");
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setFechaVencimiento(LocalDate.now().plusDays(7));
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setPrioridad(Prioridad.ALTA);

        when(tareaService.listarTareas()).thenReturn(Arrays.asList(tarea));

        // Act & Assert
        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTareaPorId_DebeRetornarTarea() throws Exception {
        // Arrange
        TareaResponseDTO tarea = new TareaResponseDTO();
        tarea.setId(1L);
        tarea.setNombre("Tarea 1");
        tarea.setDescripcion("Descripción");
        tarea.setProyectoId(1L);
        tarea.setProyectoNombre("Proyecto 1");
        tarea.setEmpleadoAsignadoId(1L);
        tarea.setEmpleadoAsignadoNombre("Juan");
        tarea.setFechaCreacion(LocalDateTime.now());
        tarea.setFechaVencimiento(LocalDate.now().plusDays(7));
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setPrioridad(Prioridad.ALTA);

        when(tareaService.obtenerPorId(1L)).thenReturn(tarea);

        // Act & Assert
        mockMvc.perform(get("/api/tareas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void crearTarea_DebeRetornarTareaCreada() throws Exception {
        // Arrange
        TareaCreateDTO createDTO = new TareaCreateDTO(
                "Nueva Tarea",
                "Descripción",
                1L,
                1L,
                LocalDate.now().plusDays(7),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA
        );

        TareaResponseDTO responseDTO = new TareaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Nueva Tarea");
        responseDTO.setDescripcion("Descripción");
        responseDTO.setProyectoId(1L);
        responseDTO.setProyectoNombre("Proyecto 1");
        responseDTO.setEmpleadoAsignadoId(1L);
        responseDTO.setEmpleadoAsignadoNombre("Juan");
        responseDTO.setFechaCreacion(LocalDateTime.now());
        responseDTO.setFechaVencimiento(LocalDate.now().plusDays(7));
        responseDTO.setEstado(EstadoTarea.PENDIENTE);
        responseDTO.setPrioridad(Prioridad.ALTA);

        when(tareaService.crearTarea(any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarTarea_DebeRetornarTareaActualizada() throws Exception {
        // Arrange
        TareaUpdateDTO updateDTO = new TareaUpdateDTO(
                "Tarea Actualizada",
                "Nueva descripción",
                1L,
                LocalDate.now().plusDays(10),
                EstadoTarea.EN_PROGRESO,
                Prioridad.MEDIA
        );

        TareaResponseDTO responseDTO = new TareaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setNombre("Tarea Actualizada");
        responseDTO.setDescripcion("Nueva descripción");
        responseDTO.setProyectoId(1L);
        responseDTO.setProyectoNombre("Proyecto 1");
        responseDTO.setEmpleadoAsignadoId(1L);
        responseDTO.setEmpleadoAsignadoNombre("Juan");
        responseDTO.setFechaCreacion(LocalDateTime.now());
        responseDTO.setFechaVencimiento(LocalDate.now().plusDays(10));
        responseDTO.setEstado(EstadoTarea.EN_PROGRESO);
        responseDTO.setPrioridad(Prioridad.MEDIA);

        when(tareaService.actualizarTarea(eq(1L), any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(put("/api/tareas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarTarea_DebeRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(tareaService).eliminarTarea(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tareas/1"))
                .andExpect(status().isNoContent());
    }
}