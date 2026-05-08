package com.adityasangani.orchestrator.model;

import java.util.List;

import lombok.Data;

@Data
public class Task {
    private String id;
    private String name;
    private TaskStatus taskStatus;
    private List<String> dependencies;
    
}
