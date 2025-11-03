package com.equipo7.apigestorproyectos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.services.RegistroHorasService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/registrohoras")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class RegistroHorasController {

    @Autowired
    private RegistroHorasService registroHorasService;

    // obtener todos los registros
    @GetMapping
    public ResponseEntity<List<RegistroHorasDTO>> listarRegistros() {
        List<RegistroHorasDTO> lista = registroHorasService.listarRegistros();
        return ResponseEntity.ok(lista);
    }

    // obtener registro por id
    @GetMapping("/{id}")
    public ResponseEntity<RegistroHorasDTO> obtenerPorId(@PathVariable Long id) {
        RegistroHorasDTO registro = registroHorasService.obtenerPorId(id);
        return ResponseEntity.ok(registro);
    }

    // guardar un nuevo registro
    @PostMapping
    public ResponseEntity<RegistroHorasDTO> guardar(@RequestBody RegistroHorasDTO dto) {
        RegistroHorasDTO nuevo = registroHorasService.guardarRegistro(dto);
        return ResponseEntity.ok(nuevo);
    }

    // eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        registroHorasService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }
}
