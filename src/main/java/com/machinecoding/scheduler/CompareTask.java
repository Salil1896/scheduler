package com.machinecoding.scheduler;

import java.util.Comparator;

class CompareTask implements Comparator<MyRunnable> {
    public int compare(MyRunnable r1, MyRunnable r2) {
        return (int) (r1.getScheduleTime() - r2.getScheduleTime());
    }
}