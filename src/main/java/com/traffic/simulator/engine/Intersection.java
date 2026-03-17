package com.traffic.simulator.engine;

import com.traffic.simulator.entity.Vehicle;

import java.util.concurrent.Semaphore;

public class Intersection {
    private Semaphore semaphore = new Semaphore(1); // Only one vehicle at a time

    public boolean tryEnter(Vehicle v, long timeout, java.util.concurrent.TimeUnit unit) throws InterruptedException {
        return semaphore.tryAcquire(timeout, unit);
    }

    public void leave(Vehicle v) {
        semaphore.release();
    }
}
