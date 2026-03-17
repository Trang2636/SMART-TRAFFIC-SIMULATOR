package com.traffic.simulator.engine;

import com.traffic.simulator.entity.Lane;
import com.traffic.simulator.entity.Vehicle;
import com.traffic.simulator.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class TrafficEnvironment {
    private List<Lane> lanes = new ArrayList<>();

    public TrafficEnvironment() {
        this(4);
    }

    public TrafficEnvironment(int numLanes) {
        for (int i = 0; i < numLanes; i++) {
            lanes.add(new Lane());
        }
    }

    public void addVehicle(Vehicle v) {
        int laneIndex = RandomUtil.nextInt(lanes.size());
        lanes.get(laneIndex).addVehicle(v);
    }

    public void removeVehicle(Vehicle v) {
        for (Lane lane : lanes) {
            if (lane.getVehicles().remove(v)) {
                break;
            }
        }
    }

    public int getWaitingVehiclesCount() {
        return lanes.stream().mapToInt(Lane::getVehicleCount).sum();
    }

    public List<Lane> getLanes() {
        return lanes;
    }
}
