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
    private TrafficClient trafficClient; // Inyectamos el cliente

    @GetMapping("/test")
    public String probarEmergencia() {
        // 1. Mensaje propio de este servicio
        String mensajeEmergencia = "🚒 BOMBEROS EN CAMINO (Emergency Service)";

        // 2. Consultamos al servicio de tráfico
        String estadoTrafico;
        try {
            estadoTrafico = trafficClient.obtenerEstadoSemaforos();
        } catch (Exception e) {
            estadoTrafico = "❌ No se pudo conectar con Tráfico";
        }

        // 3. Devolvemos el resultado combinado
        return mensajeEmergencia + " || Reporte de Tráfico: " + estadoTrafico;
    }
}