package com.machinecoding.scheduler;


/**
 * @author salil.mamodiya
 * 02/06/21
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        SchedulerService schedulerService = new SchedulerServiceImpl(10);

        schedulerService.schedule(new SimpleTask("Task1"), 5000);
        schedulerService.schedule(new SimpleTask("Task2"), 10000);
        schedulerService.schedule(new SimpleTask("Task3"), 3000);
        System.out.println("Task scheduled");

    }
}