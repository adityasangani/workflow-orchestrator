package com.adityasangani.orchestrator.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.adityasangani.orchestrator.callback.TaskCompletionCallback;
import com.adityasangani.orchestrator.model.Task;
import com.adityasangani.orchestrator.model.TaskContext;
import com.adityasangani.orchestrator.model.TaskStatus;
import com.adityasangani.orchestrator.queue.TaskQueue;

import jakarta.annotation.PostConstruct;

@Component
public class WorkerPool {

    private final static Logger log = LoggerFactory.getLogger(WorkerPool.class);
    
    private final TaskQueue taskQueue;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private final TaskCompletionCallback completionCallback;

    public WorkerPool(TaskQueue taskQueue, TaskCompletionCallback completionCallback){
        this.taskQueue = taskQueue;
        this.completionCallback = completionCallback;
    }

    @PostConstruct
    public void startWorkers(){
        for(int i=0; i<3; i++){
            executorService.submit(() -> {

                while(true){
                    try {
                        TaskContext taskContext = taskQueue.take();
                        executeTask(taskContext.getTask());
                        completionCallback.onTaskCompleted(taskContext);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                        log.error("Error executing task", e);
                    }
                }
            });
        }
    }

    // dummy simulating executing task. This is where actual task would be executed by hitting some external api and getting back a response.
    private void executeTask(Task task) throws InterruptedException{
        task.setTaskStatus(TaskStatus.RUNNING);

        log.info("{} executing task {}.", Thread.currentThread().getName(), task.getId());

        Thread.sleep(5000);

        task.setTaskStatus(TaskStatus.COMPLETED);

        log.info("{} completed.", task.getId());
    }

}
