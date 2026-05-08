package com.adityasangani.orchestrator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adityasangani.orchestrator.model.Workflow;
import com.adityasangani.orchestrator.service.WorkflowService;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {
    
    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService){
        this.workflowService = workflowService;
    }

    @PostMapping
    public Workflow createWorkflow(@RequestBody Workflow workflow){
        return workflowService.createWorkflow(workflow);
    }

    @GetMapping("/search")
    public Workflow getWorkflowById(@RequestParam(value="workflowId") String workflowId){
        return workflowService.getWorkflowById(workflowId);
    }

}
