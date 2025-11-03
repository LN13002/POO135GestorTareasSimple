package com.equipo7.apigestorproyectos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.equipo7.apigestorproyectos.models.Tarea;
import com.equipo7.apigestorproyectos.services.TareaService;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*") 
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping
    public List<Tarea> listarTareas() {
        return tareaService.listarTareas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerTarea(@PathVariable Long id) {
        return tareaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        Tarea nuevaTarea = tareaService.guardarTarea(tarea);
        return ResponseEntity.ok(nuevaTarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        return tareaService.obtenerPorId(id)
                .map(tarea -> {
                    tarea.setNombre(tareaActualizada.getNombre());
                    tarea.setDescripcion(tareaActualizada.getDescripcion());
                    tarea.setFechaVencimiento(tareaActualizada.getFechaVencimiento());
                    tarea.setEstado(tareaActualizada.getEstado());
                    tarea.setPrioridad(tareaActualizada.getPrioridad());
                    tarea.setEmpleadoAsignado(tareaActualizada.getEmpleadoAsignado());
                    return ResponseEntity.ok(tareaService.guardarTarea(tarea));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }
}
