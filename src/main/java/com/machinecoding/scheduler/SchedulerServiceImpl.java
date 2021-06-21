package com.machinecoding.scheduler;

import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author salil.mamodiya
 * 21/06/21
 */
public class SchedulerServiceImpl implements SchedulerService {


    private PriorityQueue<MyRunnable> queue;
    private ReentrantLock consumeLock = new ReentrantLock();
    private Condition condition = consumeLock.newCondition();

    private Semaphore prodSem = new Semaphore(1);
    private Semaphore threadLimitSem;

    public SchedulerServiceImpl(Integer maxThread) {
        threadLimitSem = new Semaphore(maxThread);
        queue = new PriorityQueue<>(new CompareTask());
        Thread taskRunner = new Thread(new TaskRunner(queue, consumeLock, condition, prodSem, threadLimitSem));
        taskRunner.start();
    }

    public void schedule(Runnable task, long delay) throws InterruptedException {
        consumeLock.lock();
        if (queue.size() == 0) {
            condition.signalAll();
        }


        prodSem.acquire();
        queue.add(new MyRunnable(task, new Date().getTime() + delay, threadLimitSem));
        prodSem.release();
        consumeLock.unlock();
    }
}
