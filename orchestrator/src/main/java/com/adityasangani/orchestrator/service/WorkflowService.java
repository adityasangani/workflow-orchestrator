package com.adityasangani.orchestrator.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.adityasangani.orchestrator.model.Workflow;
import com.adityasangani.orchestrator.model.WorkflowStatus;
import com.adityasangani.orchestrator.repository.WorkflowRepository;

@Service
public class WorkflowService {
    
    private final WorkflowRepository workflowRepository;

    public WorkflowService(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public Workflow createWorkflow(Workflow workflow){
        workflow.setWorkflowId(UUID.randomUUID().toString());
        workflow.setWorkflowStatus(WorkflowStatus.CREATED);
        workflowRepository.save(workflow);
        return workflow;
    }

    public Workflow getWorkflowById(String workflowId){
        System.out.println("Searching for ID: " + workflowId); 
        Workflow workflow = workflowRepository.getById(workflowId);
        System.out.println("Found: " + workflow);
        if(workflow==null){
            return null;
        }
        return workflow;
    }
}
