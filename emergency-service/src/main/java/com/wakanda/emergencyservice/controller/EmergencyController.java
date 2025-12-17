package com.wakanda.emergencyservice.controller;

import com.wakanda.emergencyservice.client.TrafficClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emergency")
public class EmergencyController {

    @Autowired
    private TrafficClient trafficClient;

    @GetMapping("/test")
    public String probarEmergencia() {
        String mensajeEmergencia = "🚒 BOMBEROS EN CAMINO (Emergency Service)";

        String estadoTrafico;
        try {
            estadoTrafico = trafficClient.obtenerEstadoSemaforos();
        } catch (Exception e) {
            estadoTrafico = "❌ No se pudo conectar con Tráfico";
        }

        return mensajeEmergencia + " || Reporte de Tráfico: " + estadoTrafico;
    }
}