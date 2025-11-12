package com.equipo7.apigestorproyectos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.equipo7.apigestorproyectos.controllers.TareaController;
import com.equipo7.apigestorproyectos.dto.respuesta.TareaResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaUpdateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.models.EstadoTarea;
import com.equipo7.apigestorproyectos.models.Prioridad;
import com.equipo7.apigestorproyectos.services.TareaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

@WebMvcTest(TareaController.class)
@DisplayName("Tests del Controller de Tareas")
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TareaService tareaService;

    @Test
    @DisplayName("GET /api/tareas - Debe listar todas las tareas exitosamente")
    void debeListarTodasLasTareasExitosamente() throws Exception {
        TareaResponseDTO tarea1 = crearTareaResponseDTO(1L, "Tarea 1");
        TareaResponseDTO tarea2 = crearTareaResponseDTO(2L, "Tarea 2");
        List<TareaResponseDTO> tareas = Arrays.asList(tarea1, tarea2);

        when(tareaService.listarTareas()).thenReturn(tareas);

        mockMvc.perform(get("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Tarea 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("Tarea 2"));
    }

    @Test
    @DisplayName("GET /api/tareas - Debe retornar lista vacía cuando no hay tareas")
    void debeRetornarListaVaciaCuandoNoHayTareas() throws Exception {
        when(tareaService.listarTareas()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/tareas/{id} - Debe obtener tarea por ID exitosamente")
    void debeObtenerTareaPorIdExitosamente() throws Exception {
        Long tareaId = 1L;
        TareaResponseDTO tarea = crearTareaResponseDTO(tareaId, "Tarea Test");

        when(tareaService.obtenerPorId(tareaId)).thenReturn(tarea);

        mockMvc.perform(get("/api/tareas/{id}", tareaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(tareaId))
                .andExpect(jsonPath("$.nombre").value("Tarea Test"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/tareas/{id} - Debe retornar 404 cuando tarea no existe")
    void debeRetornar404CuandoTareaNoExiste() throws Exception {
        Long tareaId = 999L;
        when(tareaService.obtenerPorId(tareaId))
                .thenThrow(new ResourceNotFoundException("Tarea no encontrada con ID: " + tareaId));

        mockMvc.perform(get("/api/tareas/{id}", tareaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/tareas - Debe crear tarea exitosamente")
    void debeCrearTareaExitosamente() throws Exception {
        TareaCreateDTO createDTO = new TareaCreateDTO(
                "Nueva Tarea",
                "Descripción de prueba",
                1L, // proyectoId
                2L, // empleadoAsignadoId
                LocalDate.now().plusDays(7),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA);

        TareaResponseDTO responseDTO = crearTareaResponseDTO(1L, "Nueva Tarea");

        when(tareaService.crearTarea(any(TareaCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Nueva Tarea"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }

    @Test
    @DisplayName("POST /api/tareas - Debe retornar 400 cuando datos son inválidos")
    void debeRetornar400CuandoDatosSonInvalidos() throws Exception {
        TareaCreateDTO createDTO = new TareaCreateDTO(
                "", // nombre vacío (inválido)
                "Descripción",
                1L,
                2L,
                LocalDate.now(),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA);

        mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/tareas - Debe retornar 404 cuando proyecto no existe")
    void debeRetornar404CuandoProyectoNoExiste() throws Exception {
        TareaCreateDTO createDTO = new TareaCreateDTO(
                "Nueva Tarea",
                "Descripción",
                999L, // proyectoId que no existe
                2L,
                LocalDate.now(),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA);

        when(tareaService.crearTarea(any(TareaCreateDTO.class)))
                .thenThrow(new ResourceNotFoundException("Proyecto no encontrado"));

        mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isNotFound());
    }

    // @Test
    // @DisplayName("PUT /api/tareas/{id} - Debe actualizar tarea exitosamente")
    // void debeActualizarTareaExitosamente() throws Exception {
    // Long tareaId = 1L;
    // TareaUpdateDTO updateDTO = new TareaUpdateDTO(
    // "Tarea Actualizada",
    // "Nueva descripción",
    // 3L, // nuevo empleadoAsignadoId
    // LocalDate.now().plusDays(10),
    // EstadoTarea.EN_PROGRESO,
    // Prioridad.MEDIA);

    // TareaResponseDTO responseDTO = crearTareaResponseDTO(tareaId, "Tarea
    // Actualizada");
    // responseDTO = new TareaResponseDTO(
    // responseDTO.id(),
    // "Tarea Actualizada",
    // responseDTO.descripcion(),
    // responseDTO.proyectoId(),
    // responseDTO.proyectoNombre(),
    // responseDTO.empleadoAsignadoId(),
    // responseDTO.empleadoAsignadoNombre(),
    // responseDTO.fechaCreacion(),
    // responseDTO.fechaVencimiento(),
    // EstadoTarea.EN_PROGRESO,
    // Prioridad.MEDIA);

    // when(tareaService.actualizarTarea(eq(tareaId), any(TareaUpdateDTO.class)))
    // .thenReturn(responseDTO);

    // mockMvc.perform(put("/api/tareas/{id}", tareaId)
    // .contentType(MediaType.APPLICATION_JSON)
    // .content(objectMapper.writeValueAsString(updateDTO)))
    // .andExpect(status().isOk())
    // .andExpect(jsonPath("$.id").value(tareaId))
    // .andExpect(jsonPath("$.nombre").value("Tarea Actualizada"))
    // .andExpect(jsonPath("$.estado").value("EN_PROGRESO"));
    // }

    @Test
    @DisplayName("PUT /api/tareas/{id} - Debe retornar 404 cuando tarea no existe")
    void debeRetornar404AlActualizarTareaNoExistente() throws Exception {
        Long tareaId = 999L;
        TareaUpdateDTO updateDTO = new TareaUpdateDTO(
                "Tarea Actualizada",
                "Descripción",
                2L,
                LocalDate.now(),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA);

        when(tareaService.actualizarTarea(eq(tareaId), any(TareaUpdateDTO.class)))
                .thenThrow(new ResourceNotFoundException("Tarea no encontrada"));

        mockMvc.perform(put("/api/tareas/{id}", tareaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/tareas/{id} - Debe eliminar tarea exitosamente")
    void debeEliminarTareaExitosamente() throws Exception {
        Long tareaId = 1L;
        doNothing().when(tareaService).eliminarTarea(tareaId);

        mockMvc.perform(delete("/api/tareas/{id}", tareaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/tareas/{id} - Debe retornar 404 cuando tarea no existe")
    void debeRetornar404AlEliminarTareaNoExistente() throws Exception {
        Long tareaId = 999L;
        doThrow(new ResourceNotFoundException("Tarea no encontrada"))
                .when(tareaService).eliminarTarea(tareaId);

        mockMvc.perform(delete("/api/tareas/{id}", tareaId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private TareaResponseDTO crearTareaResponseDTO(Long id, String nombre) {
        return new TareaResponseDTO(
                id,
                nombre,
                "Descripción de " + nombre,
                1L, // proyectoId
                "Proyecto Test",
                2L, // empleadoAsignadoId
                "Juan Pérez",
                LocalDateTime.now(),
                LocalDate.now().plusDays(7),
                EstadoTarea.PENDIENTE,
                Prioridad.ALTA);
    }
}
