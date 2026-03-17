package main.java.com.traffic.simulator.entity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Lane {

    private final String laneId;
    private final BlockingQueue<Vehicle> queue;
    private final int capacity;

    public Lane(String laneId, int capacity) {
        this.laneId = laneId;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue<>(capacity);
    }

    // Xe vào làn (khi gặp đèn đỏ)
    public void addVehicle(Vehicle vehicle) throws InterruptedException {
        queue.put(vehicle); // block nếu full
        System.out.println(vehicle.getName() + " vào hàng chờ tại " + laneId);
    }

    // Xe rời làn (khi đèn xanh)
    public Vehicle pollVehicle() {
        Vehicle v = queue.poll(); // không block
        if (v != null) {
            System.out.println(v.getName() + " rời hàng chờ tại " + laneId);
        }
        return v;
    }

    // Lấy xe (blocking) → dùng khi cần chờ
    public Vehicle takeVehicle() throws InterruptedException {
        Vehicle v = queue.take();
        System.out.println(v.getName() + " được phép đi từ " + laneId);
        return v;
    }

    public int getSize() {
        return queue.size();
    }

    public boolean isFull() {
        return queue.size() >= capacity;
    }

    public String getLaneId() {
        return laneId;
    }
}