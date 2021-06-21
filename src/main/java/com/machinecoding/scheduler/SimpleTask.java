package com.machinecoding.scheduler;


/**
 * @author salil.mamodiya
 * 21/06/21
 */
public class SimpleTask implements Runnable {

    private String taskId;

    public SimpleTask(String taskId) {
        this.taskId = taskId;
    }

    public void run() {
        System.out.println("Running Task " + taskId);
    }
}
