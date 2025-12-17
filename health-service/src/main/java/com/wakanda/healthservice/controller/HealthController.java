package com.wakanda.healthservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping("/check")
    public String healthCheck() {
        return "🏥 [WAKANDA HEALTH] Hospital Central Operativo. Camas UCI disponibles: 12. Médicos de guardia: 5.";
    }
}