package main.java.com.traffic.simulator.engine;

import main.java.com.traffic.simulator.entity.Lane;
import main.java.com.traffic.simulator.entity.Vehicle;

public class LaneController {

    private final main.java.com.traffic.simulator.entity.Lane lane;

    public LaneController(Lane lane) {
        this.lane = lane;
    }

    // Gọi khi đèn chuyển sang GREEN
    public void releaseVehicles() {
        System.out.println(">>> Đèn XANH - cho xe đi tại " + lane.getLaneId());

        while (lane.getSize() > 0) {
            Vehicle v = lane.pollVehicle();
            if (v != null) {
                v.passIntersection();
            }
        }
    }
}