package com.adityasangani.orchestrator.scheduler;

import org.springframework.stereotype.Component;

import com.adityasangani.orchestrator.model.Task;
import com.adityasangani.orchestrator.model.TaskStatus;
import com.adityasangani.orchestrator.model.Workflow;

@Component
public class DependencyResolver {
    
    // in workflow if dependencies complete, make task ready
    public void updatePendingToReadyTasks(Workflow workflow){
        for(Task task : workflow.getTasks()){
            if(task.getTaskStatus()!=TaskStatus.PENDING){
                continue;
            }

            boolean ready = task.getDependencies()
                .stream()
                .allMatch(dep -> isDependencyCompleted(workflow, dep));
            
            if(ready){
                task.setTaskStatus(TaskStatus.READY);
            }
        }
    }

    private boolean isDependencyCompleted(Workflow workflow, String dependencyId){
        return workflow.getTasks()
            .stream()
            .filter(task -> 
                    task.getId().equals(dependencyId)
            )
            .findFirst()
            .map(t -> 
                    t.getTaskStatus() == TaskStatus.COMPLETED
            )
            .orElse(false);
    }
}
