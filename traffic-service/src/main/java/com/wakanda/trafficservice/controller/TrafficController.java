package com.wakanda.trafficservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/traffic")
public class TrafficController {

    private static final String TRAFFIC_CONFIG = "trafficService";

    @GetMapping("/semaforos")
    @CircuitBreaker(name = TRAFFIC_CONFIG, fallbackMethod = "fallbackSemaforos")
    @Bulkhead(name = TRAFFIC_CONFIG, fallbackMethod = "fallbackSemaforos")
    public String obtenerEstadoSemaforos() {

        randomlyFailOrSleep();

        return "🟢 [WAKANDA TRAFFIC] Norte: VERDE | Sur: ROJO | Flujo: Óptimo";
    }

    public String fallbackSemaforos(Throwable t) {
        return "⚠️ [MODO EMERGENCIA] Sensores no responden. Semáforos en ÁMBAR intermitente. Error: " + t.getMessage();
    }

    private void randomlyFailOrSleep() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10);

        if (randomNum < 3) {
            throw new RuntimeException("Fallo de conexión con sensor IoT-X55");
        }
    }
}