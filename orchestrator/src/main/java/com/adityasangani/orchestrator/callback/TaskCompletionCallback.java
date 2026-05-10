package com.adityasangani.orchestrator.callback;

import com.adityasangani.orchestrator.model.TaskContext;

@FunctionalInterface
public interface TaskCompletionCallback {
        
    public void onTaskCompleted(TaskContext taskContext);
}
