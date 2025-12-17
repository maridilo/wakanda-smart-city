package com.wakanda.emergencyservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "traffic-service")
public interface TrafficClient {

    @GetMapping("/api/v1/traffic/semaforos")
    String obtenerEstadoSemaforos();
}