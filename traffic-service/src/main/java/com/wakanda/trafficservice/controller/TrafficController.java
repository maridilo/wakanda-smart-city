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

    // Nombre de la instancia que configuraremos en el application.yml
    private static final String TRAFFIC_CONFIG = "trafficService";

    /**
     * Endpoint: Gestión de Semáforos Inteligentes
     * Simula la obtención de datos en tiempo real de los sensores de Wakanda.
     */
    @GetMapping("/semaforos")
    // Implementación del Patrón CIRCUIT BREAKER (Evita fallos en cascada - PDF pág. 15)
    @CircuitBreaker(name = TRAFFIC_CONFIG, fallbackMethod = "fallbackSemaforos")
    // Implementación del Patrón BULKHEAD (Aislamiento de recursos - PDF pág. 19)
    @Bulkhead(name = TRAFFIC_CONFIG, fallbackMethod = "fallbackSemaforos")
    public String obtenerEstadoSemaforos() {

        // Simulación de lógica de negocio:
        // A veces los sensores de la ciudad pueden fallar o ir lentos.
        randomlyFailOrSleep();

        return "🟢 [WAKANDA TRAFFIC] Norte: VERDE | Sur: ROJO | Flujo: Óptimo";
    }

    /**
     * FALLBACK METHOD
     * Este metodo se ejecuta automáticamente si:
     * 1. El servicio falla (Exception).
     * 2. El circuito está ABIERTO (Circuit Breaker).
     * 3. Hay demasiadas peticiones simultáneas (Bulkhead).
     * * Referencia PDF pág 16: "Retornar una respuesta predefinida"
     */
    public String fallbackSemaforos(Throwable t) {
        // En caso de emergencia, los semáforos entran en modo precaución
        return "⚠️ [MODO EMERGENCIA] Sensores no responden. Semáforos en ÁMBAR intermitente. Error: " + t.getMessage();
    }

    // Metodo auxiliar para simular fallos aleatorios (Solo para pruebas)
    private void randomlyFailOrSleep() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10);

        // 30% de probabilidad de fallo (simula sensor roto)
        if (randomNum < 3) {
            throw new RuntimeException("Fallo de conexión con sensor IoT-X55");
        }

        // Simular latencia (opcional, para probar timeouts)
        // try { Thread.sleep(100); } catch (InterruptedException e) {}
    }
}