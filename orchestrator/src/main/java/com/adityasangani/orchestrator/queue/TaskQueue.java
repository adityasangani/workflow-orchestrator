package com.adityasangani.orchestrator.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.adityasangani.orchestrator.model.Task;

@Component
public class TaskQueue {
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public void submit(Task task){
        queue.offer(task);
    }

    public Task take() throws InterruptedException{
        return queue.take();
    }

}
