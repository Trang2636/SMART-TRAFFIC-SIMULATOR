package com.traffic.simulator.engine;

import com.traffic.simulator.entity.Lane;
import com.traffic.simulator.entity.Vehicle;
import com.traffic.simulator.entity.VehicleType;
import com.traffic.simulator.exception.TrafficJamException;
import com.traffic.simulator.util.LoggerUtil;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TrafficMonitor {
    private TrafficEnvironment environment;
    private ScheduledExecutorService scheduler;
    private AtomicInteger totalPassed = new AtomicInteger(0);
    private static final int JAM_THRESHOLD = 10;

    public TrafficMonitor(TrafficEnvironment environment) {
        this.environment = environment;
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::monitor, 0, 5, TimeUnit.SECONDS);
    }

    public void stop() {
        scheduler.shutdown();
    }

    private void monitor() {
        List<Lane> lanes = environment.getLanes();
        long standardCount = lanes.stream()
                .flatMap(lane -> lane.getVehicles().stream())
                .filter(v -> v.getType() == VehicleType.STANDARD)
                .count();
        long priorityCount = lanes.stream()
                .flatMap(lane -> lane.getVehicles().stream())
                .filter(v -> v.getType() == VehicleType.PRIORITY)
                .count();
        int totalWaiting = (int) (standardCount + priorityCount);
        int totalPassedCount = totalPassed.get();

        LoggerUtil.log("Monitor: Standard vehicles: " + standardCount + ", Priority vehicles: " + priorityCount + ", Total waiting: " + totalWaiting + ", Total passed: " + totalPassedCount);

        // Check for jam
        for (Lane lane : lanes) {
            if (lane.getVehicleCount() > JAM_THRESHOLD) {
                try {
                    throw new TrafficJamException("Traffic jam detected in lane with " + lane.getVehicleCount() + " vehicles");
                } catch (TrafficJamException e) {
                    LoggerUtil.log("Jam: " + e.getMessage());
                }
            }
        }
    }

    public void vehiclePassed(Vehicle v) {
        totalPassed.incrementAndGet();
    }
}
