package com.adityasangani.orchestrator.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/health")
    public Map<String, String> hello(){
        return Map.of(
            "message", "Hello there.",
            "by", "Aditya Sangani"
        );
    }
}
