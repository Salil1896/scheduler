package com.machinecoding.scheduler;

import java.util.concurrent.Semaphore;

/**
 * @author salil.mamodiya
 * 21/06/21
 */
public class MyRunnable implements Runnable {

    private Runnable task;
    private Long scheduleTime;
    private Semaphore threadLimitSemaphore;

    public MyRunnable(Runnable task,
                      Long scheduleTime,
                      Semaphore threadLimitSemaphore
    ) {
        this.task = task;
        this.scheduleTime = scheduleTime;
        this.threadLimitSemaphore = threadLimitSemaphore;
    }

    public void run() {
        task.run();
        threadLimitSemaphore.release();
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public Long getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Long scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}
