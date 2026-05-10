package com.adityasangani.orchestrator.callback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adityasangani.orchestrator.model.Task;
import com.adityasangani.orchestrator.model.TaskContext;
import com.adityasangani.orchestrator.model.Workflow;
import com.adityasangani.orchestrator.queue.TaskQueue;
import com.adityasangani.orchestrator.scheduler.DependencyResolver;

@Component
public class DependencyResolvingCallback implements TaskCompletionCallback{

    private static final Logger log = LoggerFactory.getLogger(DependencyResolvingCallback.class);

    private final DependencyResolver dependencyResolver;
    private final TaskQueue taskQueue;

    public DependencyResolvingCallback(DependencyResolver dependencyResolver, TaskQueue taskQueue){
        this.dependencyResolver = dependencyResolver;
        this.taskQueue = taskQueue;
    }

    @Override
    public void onTaskCompleted(TaskContext taskContext) {
        Workflow workflow = taskContext.getWorkflow();

        log.info("Task {} completed in workflow {}. Resolving dependencies...", taskContext.getTask().getId(), workflow.getWorkflowId());

        synchronized(workflow){
            List<Task> newlyReadyTasks = dependencyResolver.updatePendingToReadyTasks(workflow);
            for(Task readyTask : newlyReadyTasks){
                log.info("Task {} is now READY. Enqueuing for execution.", readyTask.getId());
                taskQueue.submit(new TaskContext(workflow, readyTask));
            }
        }
    }
    
}
