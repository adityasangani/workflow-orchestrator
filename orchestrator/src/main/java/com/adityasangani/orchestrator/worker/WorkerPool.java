package com.adityasangani.orchestrator.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.adityasangani.orchestrator.model.Task;
import com.adityasangani.orchestrator.model.TaskStatus;
import com.adityasangani.orchestrator.queue.TaskQueue;

import jakarta.annotation.PostConstruct;

@Component
public class WorkerPool {
    
    private final TaskQueue taskQueue;

    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public WorkerPool(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
    }

    @PostConstruct
    public void startWorkers(){
        for(int i=0; i<3; i++){
            executorService.submit(() -> {

                while(true){
                    try {
                        Task task = taskQueue.take();
                        executeTask(task);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    // dummy simulating executing task. This is where actual task would be executed by hitting some external api and getting back a response.
    private void executeTask(Task task) throws InterruptedException{
        task.setTaskStatus(TaskStatus.RUNNING);

        System.out.println(
            Thread.currentThread().getName()
             + " executing task "
             + task.getId()
        );

        Thread.sleep(5000);

        task.setTaskStatus(TaskStatus.COMPLETED);

        System.out.println(task.getId() + " completed");
    }

}
