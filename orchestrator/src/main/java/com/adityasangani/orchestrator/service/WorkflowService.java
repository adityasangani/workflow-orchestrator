package com.adityasangani.orchestrator.service;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.adityasangani.orchestrator.model.TaskStatus;
import com.adityasangani.orchestrator.model.Workflow;
import com.adityasangani.orchestrator.model.WorkflowStatus;
import com.adityasangani.orchestrator.repository.WorkflowRepository;
import com.adityasangani.orchestrator.scheduler.DependencyResolver;

@Service
public class WorkflowService {
    
    private final WorkflowRepository workflowRepository;
    private final DependencyResolver dependencyResolver;


    public WorkflowService(WorkflowRepository workflowRepository, DependencyResolver dependencyResolver){
        this.workflowRepository = workflowRepository;
        this.dependencyResolver = dependencyResolver;
    }

    public Workflow createWorkflow(Workflow workflow){
        workflow.setWorkflowId(UUID.randomUUID().toString());
        workflow.setWorkflowStatus(WorkflowStatus.CREATED);
        workflow.getTasks().forEach(task -> {
            if(task.getDependencies() == null || task.getDependencies().isEmpty()){
                task.setTaskStatus(TaskStatus.READY);
            } else{
                task.setTaskStatus(TaskStatus.PENDING);
            }
        });
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

    public Workflow completeTask(String workflowId, String taskId){
        Workflow workflow = workflowRepository.getById(workflowId);

        workflow.getTasks().stream()
            .filter(entity -> entity.getId().equals(taskId))
            .findFirst()
            .ifPresent(t -> t.setTaskStatus(TaskStatus.COMPLETED));

        dependencyResolver.updatePendingToReadyTasks(workflow);
        return workflow;

    }
}
