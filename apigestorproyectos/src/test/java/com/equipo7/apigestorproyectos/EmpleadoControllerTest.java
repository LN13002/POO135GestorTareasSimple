package com.equipo7.apigestorproyectos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
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
import org.springframework.http.MediaType;

import com.equipo7.apigestorproyectos.controllers.RegistroHorasController;
import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.RegistroHorasCreateDTO;
import com.equipo7.apigestorproyectos.exceptions.ResourceNotFoundException;
import com.equipo7.apigestorproyectos.services.RegistroHorasService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RegistroHorasController.class)
@DisplayName("Tests del Controller de Registro de Horas")
public class EmpleadoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegistroHorasService registroHorasService;

    @Test
    @DisplayName("GET /api/registrohoras - Debe listar todos los registros")
    void debeListarTodosLosRegistros() throws Exception {
        RegistroHorasDTO registro1 = crearRegistroHorasDTO(1L, new BigDecimal("8.0"));
        RegistroHorasDTO registro2 = crearRegistroHorasDTO(2L, new BigDecimal("6.5"));
        List<RegistroHorasDTO> registros = Arrays.asList(registro1, registro2);

        when(registroHorasService.listarRegistros()).thenReturn(registros);

        mockMvc.perform(get("/api/registrohoras")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].horasRegistradas").value(8.0))
                .andExpect(jsonPath("$[1].horasRegistradas").value(6.5));
    }

    @Test
    @DisplayName("GET /api/registrohoras/{id} - Debe obtener registro por ID")
    void debeObtenerRegistroPorId() throws Exception {
        Long registroId = 1L;
        RegistroHorasDTO registro = crearRegistroHorasDTO(registroId, new BigDecimal("8.5"));

        when(registroHorasService.obtenerPorId(registroId)).thenReturn(registro);

        mockMvc.perform(get("/api/registrohoras/{id}", registroId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(registroId))
                .andExpect(jsonPath("$.horasRegistradas").value(8.5));
    }

    @Test
    @DisplayName("GET /api/registrohoras/{id} - Debe retornar 404 cuando registro no existe")
    void debeRetornar404CuandoRegistroNoExiste() throws Exception {
        Long registroId = 999L;
        when(registroHorasService.obtenerPorId(registroId))
                .thenThrow(new ResourceNotFoundException("Registro no encontrado"));

        mockMvc.perform(get("/api/registrohoras/{id}", registroId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/registrohoras - Debe crear registro exitosamente")
    void debeCrearRegistroExitosamente() throws Exception {
        RegistroHorasCreateDTO createDTO = new RegistroHorasCreateDTO(
                1L, // tareaId
                2L, // empleadoId
                LocalDate.now(),
                new BigDecimal("8.0"),
                "Desarrollo de funcionalidades",
                LocalDateTime.now());

        RegistroHorasDTO responseDTO = crearRegistroHorasDTO(1L, new BigDecimal("8.0"));

        when(registroHorasService.guardarRegistro(any(RegistroHorasCreateDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/registrohoras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.horasRegistradas").value(8.0));
    }

    @Test
    @DisplayName("POST /api/registrohoras - Debe retornar 400 cuando horas son inválidas")
    void debeRetornar400CuandoHorasSonInvalidas() throws Exception {
        RegistroHorasCreateDTO createDTO = new RegistroHorasCreateDTO(
                1L,
                2L,
                LocalDate.now(),
                null, // horas nulas (inválido)
                "Descripción",
                LocalDateTime.now());

        mockMvc.perform(post("/api/registrohoras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/registrohoras - Debe retornar 404 cuando empleado no existe")
    void debeRetornar404CuandoEmpleadoNoExiste() throws Exception {
        RegistroHorasCreateDTO createDTO = new RegistroHorasCreateDTO(
                1L,
                999L, // empleadoId que no existe
                LocalDate.now(),
                new BigDecimal("8.0"),
                "Descripción",
                LocalDateTime.now());

        when(registroHorasService.guardarRegistro(any(RegistroHorasCreateDTO.class)))
                .thenThrow(new ResourceNotFoundException("Empleado no encontrado"));

        mockMvc.perform(post("/api/registrohoras")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/registrohoras/{id} - Debe eliminar registro exitosamente")
    void debeEliminarRegistroExitosamente() throws Exception {
        Long registroId = 1L;
        doNothing().when(registroHorasService).eliminarRegistro(registroId);

        mockMvc.perform(delete("/api/registrohoras/{id}", registroId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/registrohoras/{id} - Debe retornar 404 cuando registro no existe")
    void debeRetornar404AlEliminarRegistroNoExistente() throws Exception {
        Long registroId = 999L;
        doThrow(new ResourceNotFoundException("Registro no encontrado"))
                .when(registroHorasService).eliminarRegistro(registroId);

        mockMvc.perform(delete("/api/registrohoras/{id}", registroId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private RegistroHorasDTO crearRegistroHorasDTO(Long id, BigDecimal horas) {
        return new RegistroHorasDTO(
                id,
                1L, // tareaId
                2L, // empleadoId
                LocalDate.now(),
                horas,
                "Desarrollo de funcionalidades",
                LocalDateTime.now());
    }
}
