package com.wakanda.wasteservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/waste")
public class WasteController {

    @GetMapping("/contenedores")
    public String verificarContenedores() {
        // Simulamos niveles de llenado aleatorios
        int nivelLlenado = new Random().nextInt(100);
        String estado = (nivelLlenado > 80) ? "🔴 LLENO (Enviar Camión)" : "🟢 CON ESPACIO";

        return "♻️ [WAKANDA WASTE] Contenedor Sector 4: " + nivelLlenado + "% | Estado: " + estado;
    }
}