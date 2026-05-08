package com.adityasangani.orchestrator.model;

import java.util.List;

import lombok.Data;

@Data
public class Workflow {
    private String workflowId;
    private String workflowName;
    private WorkflowStatus workflowStatus;
    private List<Task> tasks;   
}
