package com.traffic.simulator.entity;

public abstract class Vehicle {
    protected int id;
    protected VehicleType type;

    public Vehicle(int id, VehicleType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + " #" + id;
    }
}
