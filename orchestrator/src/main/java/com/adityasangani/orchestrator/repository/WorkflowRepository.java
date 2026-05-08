package com.adityasangani.orchestrator.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.adityasangani.orchestrator.model.Workflow;

@Repository
public class WorkflowRepository {

    private Map<String, Workflow> workflows = new HashMap<>();

    public void save(Workflow workflow){
        workflows.put(workflow.getWorkflowId(), workflow);
    }

    public Workflow getById(String workflowId){
        return workflows.get(workflowId);
    }
}
