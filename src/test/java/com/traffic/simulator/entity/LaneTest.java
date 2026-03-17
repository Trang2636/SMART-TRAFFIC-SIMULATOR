package test.java.com.traffic.simulator.entity;

import main.java.com.traffic.simulator.entity.Lane;
import main.java.com.traffic.simulator.entity.Vehicle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LaneTest {

    public static void main(String[] args) {
        main.java.com.traffic.simulator.entity.Lane lane = new Lane("Lane-1", 10);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 20; i++) {
            int id = i;
            executor.submit(() -> {
                Vehicle v = new Vehicle("Car-" + id, lane) {};
                v.run();
            });
        }

        executor.shutdown();
    }
}