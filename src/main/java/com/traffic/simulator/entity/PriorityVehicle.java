package entity;

import engine.PriorityManager;
import engine.Intersection;

public class PriorityVehicle extends Vehicle {

    public PriorityVehicle(String id, Intersection intersection) {
        super(id, intersection);
    }

    @Override
    public void run() {
        try {
            // bật ưu tiên
            PriorityManager.activate();

            move();
            // không chờ
            approachIntersection();
            enterIntersection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // tắt ưu tiên
            PriorityManager.deactivate();
        }
    }

    @Override
    protected boolean shouldWait() {
        return false;
    }

    @Override
    protected void move() {
        System.out.println(id + " đang di chuyển nhanh");
    }
}