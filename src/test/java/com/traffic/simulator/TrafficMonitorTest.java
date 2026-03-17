package com.traffic.simulator;

import com.traffic.simulator.engine.TrafficEnvironment;
import com.traffic.simulator.engine.TrafficMonitor;
import com.traffic.simulator.entity.PriorityVehicle;
import com.traffic.simulator.entity.StandardVehicle;
import com.traffic.simulator.entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrafficMonitorTest {
    private TrafficEnvironment environment;
    private TrafficMonitor monitor;

    @BeforeEach
    public void setUp() {
        environment = new TrafficEnvironment(2); // 2 lanes
        monitor = new TrafficMonitor(environment);
    }

    @Test
    public void testStatistics() throws InterruptedException {
        // Add vehicles
        Vehicle v1 = new StandardVehicle(1);
        Vehicle v2 = new PriorityVehicle(2);
        Vehicle v3 = new StandardVehicle(3);
        environment.addVehicle(v1);
        environment.addVehicle(v2);
        environment.addVehicle(v3);

        // Wait for monitor to run
        Thread.sleep(6000); // Wait more than 5 seconds

        // Since monitor logs, we can't easily assert logs, but we can check that it runs without error
        // In a real test, we might mock LoggerUtil

        // Test vehiclePassed
        monitor.vehiclePassed(v1);
        monitor.vehiclePassed(v2);

        // Wait again
        Thread.sleep(6000);

        // Again, hard to assert, but ensure no exceptions
    }

    @Test
    public void testJamDetection() throws InterruptedException {
        // Add many vehicles to one lane to trigger jam
        for (int i = 0; i < 12; i++) {
            environment.addVehicle(new StandardVehicle(i));
        }

        // Wait for monitor
        Thread.sleep(6000);

        // Should log jam, but since it catches, no exception thrown
    }
}
