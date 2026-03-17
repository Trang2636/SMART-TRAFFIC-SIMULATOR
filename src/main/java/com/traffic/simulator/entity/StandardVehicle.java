class StandardVehicle extends Vehicle package entity;

import engine.PriorityManager;
import engine.Intersection;

public class StandardVehicle extends Vehicle {

    public StandardVehicle(String id, Intersection intersection) {
        super(id, intersection);
    }

    @Override
    protected boolean shouldWait() {
        return PriorityManager.isActive() || isRedLight();
    }

    private boolean isRedLight() {
        // giả lập (sau này nối TrafficLight)
        return true;
    }

    @Override
    protected void move() {
        System.out.println(id + " Đang di chuyển bình thường");
    }
}