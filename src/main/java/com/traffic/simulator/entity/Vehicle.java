package entity;

import engine.PriorityManager;
import engine.Intersection;

public abstract class Vehicle implements Runnable {

    protected String id;
    protected Intersection intersection;

    public Vehicle(String id, Intersection intersection) {
        this.id = id;
        this.intersection = intersection;
    }

    @Override
    public void run() {
        try {
            move();
            approachIntersection();
            enterIntersection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void approachIntersection() throws InterruptedException {
        while (shouldWait()) {
            System.out.println(id + " đang đợi...");
            Thread.sleep(500);
        }
    }

    protected boolean shouldWait() {
        return PriorityManager.isActive();
    }

    protected void enterIntersection() throws Exception {
        intersection.enter(this);
    }

    protected abstract void move();

    public String getId() {
        return id;
    }
}