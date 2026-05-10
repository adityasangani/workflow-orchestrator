package com.adityasangani.orchestrator.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.adityasangani.orchestrator.model.TaskContext;

@Component
public class TaskQueue {
    private final BlockingQueue<TaskContext> queue = new LinkedBlockingQueue<>();

    public void submit(TaskContext taskContext){
        queue.offer(taskContext);
    }

    public TaskContext take() throws InterruptedException{
        return queue.take();
    }

}
