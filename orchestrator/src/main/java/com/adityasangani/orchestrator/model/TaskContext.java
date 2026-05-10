package com.adityasangani.orchestrator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskContext {
    private Workflow workflow;
    private Task task;
}
