package com.equipo7.apigestorproyectos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "¡Hola, Spring Boot - API Gestor Proyectos! 🚀";
    }
}
