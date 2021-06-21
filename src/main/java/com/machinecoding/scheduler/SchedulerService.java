package com.machinecoding.scheduler;

/**
 * @author salil.mamodiya
 * 21/06/21
 */
public interface SchedulerService {

    void schedule(Runnable task, long delay) throws InterruptedException;
}
