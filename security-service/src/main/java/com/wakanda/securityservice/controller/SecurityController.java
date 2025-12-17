package com.wakanda.securityservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {

    @GetMapping("/cameras")
    public String checkCameras() {
        boolean intruso = new Random().nextBoolean();
        if (intruso) {
            return "🚨 [WAKANDA SECURITY] ¡ALERTA! Movimiento sospechoso detectado en Sector Norte. Enviando Drones.";
        }
        return "🛡️ [WAKANDA SECURITY] Zona Segura. Sin incidentes.";
    }
}