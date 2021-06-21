package com.machinecoding.scheduler;

import java.util.Date;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author salil.mamodiya
 * 21/06/21
 */
public class TaskRunner implements Runnable {

    private PriorityQueue<MyRunnable> queue;
    private ReentrantLock consumeLock;
    private Condition condition;
    private Semaphore prodSem;
    private Semaphore threadLimitSem;

    public TaskRunner(PriorityQueue<MyRunnable> queue,
                      ReentrantLock consumeLock,
                      Condition condition,
                      Semaphore prodSem,
                      Semaphore threadLimitSem
    ) {
        this.queue = queue;
        this.consumeLock = consumeLock;
        this.condition = condition;
        this.prodSem = prodSem;
        this.threadLimitSem = threadLimitSem;
    }

    public void run() {
        try {
            while (true) {
                threadLimitSem.acquire();

                //stop the while lock if queue size is zero
                consumeLock.lock();
                while (queue.size() == 0) {
                    condition.await();
                }

                while (queue.peek().getScheduleTime() > new Date().getTime()) {
                    condition.await(Math.max(0, queue.peek().getScheduleTime() - (new Date().getTime())),
                            TimeUnit.MILLISECONDS);
                }
                consumeLock.unlock();

                prodSem.acquire();
                MyRunnable task = queue.peek();
                queue.poll();
                Thread thread = new Thread(task);
                thread.start();
                // use thread pool buffer to reuse thread
                prodSem.release();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
