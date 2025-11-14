package com.equipo7.apigestorproyectos;

import com.equipo7.apigestorproyectos.controllers.RegistroHorasController;
import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.RegistroHorasCreateDTO;
import com.equipo7.apigestorproyectos.services.RegistroHorasService;
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
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroHorasController.class)
class RegistroHorasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistroHorasService registroHorasService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listarRegistros_DebeRetornarListaDeRegistros() throws Exception {
        // Arrange
        RegistroHorasDTO registro = new RegistroHorasDTO();
        registro.setId(1L);
        registro.setTareaId(1L);
        registro.setEmpleadoId(1L);
        registro.setFecha(LocalDate.now());
        registro.setHorasRegistradas(new BigDecimal("8.0"));
        registro.setDescripcionActividad("Desarrollo");
        registro.setFechaRegistro(LocalDateTime.now());

        when(registroHorasService.listarRegistros()).thenReturn(Arrays.asList(registro));

        // Act & Assert
        mockMvc.perform(get("/api/registrohoras"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerRegistroPorId_DebeRetornarRegistro() throws Exception {
        // Arrange
        RegistroHorasDTO registro = new RegistroHorasDTO();
        registro.setId(1L);
        registro.setTareaId(1L);
        registro.setEmpleadoId(1L);
        registro.setFecha(LocalDate.now());
        registro.setHorasRegistradas(new BigDecimal("8.0"));
        registro.setDescripcionActividad("Desarrollo");
        registro.setFechaRegistro(LocalDateTime.now());

        when(registroHorasService.obtenerPorId(1L)).thenReturn(registro);

        // Act & Assert
        mockMvc.perform(get("/api/registrohoras/1"))
                .andExpect(status().isOk());
    }

    @Test
    void guardarRegistro_DebeRetornarRegistroCreado() throws Exception {
        // Arrange
        RegistroHorasCreateDTO createDTO = new RegistroHorasCreateDTO(
                1L,
                1L,
                LocalDate.now(),
                new BigDecimal("8.0"),
                "Desarrollo de funcionalidades",
                LocalDateTime.now()
        );

        RegistroHorasDTO responseDTO = new RegistroHorasDTO();
        responseDTO.setId(1L);
        responseDTO.setTareaId(1L);
        responseDTO.setEmpleadoId(1L);
        responseDTO.setFecha(LocalDate.now());
        responseDTO.setHorasRegistradas(new BigDecimal("8.0"));
        responseDTO.setDescripcionActividad("Desarrollo de funcionalidades");
        responseDTO.setFechaRegistro(LocalDateTime.now());

        when(registroHorasService.guardarRegistro(any())).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/api/registrohoras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void eliminarRegistro_DebeRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(registroHorasService).eliminarRegistro(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/registrohoras/1"))
                .andExpect(status().isNoContent());
    }
}
