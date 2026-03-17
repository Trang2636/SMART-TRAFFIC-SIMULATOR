package com.traffic.simulator.pattern.factory;

import com.traffic.simulator.entity.PriorityVehicle;
import com.traffic.simulator.entity.StandardVehicle;
import com.traffic.simulator.entity.Vehicle;
import com.traffic.simulator.util.RandomUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class VehicleFactory {
    private AtomicInteger idGenerator = new AtomicInteger(1);

    public Vehicle createRandomVehicle() {
        int id = idGenerator.getAndIncrement();
        if (RandomUtil.nextBoolean()) {
            return new StandardVehicle(id);
        } else {
            return new PriorityVehicle(id);
        }
    }
}
