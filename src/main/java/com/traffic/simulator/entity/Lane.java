package com.traffic.simulator.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Lane {
    private Queue<Vehicle> vehicles = new LinkedList<>();

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public Vehicle removeVehicle() {
        return vehicles.poll();
    }

    public int getVehicleCount() {
        return vehicles.size();
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }
}
