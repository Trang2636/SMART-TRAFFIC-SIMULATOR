package com.traffic.simulator.pattern.factory;

import com.traffic.simulator.entity.*;
import java.util.Random;

public class VehicleFactory {
    private static final Random random = new Random();
    private static int vehicleCount = 0;

    public static Vehicle createRandomVehicle() {
        int id = ++vehicleCount;
        double chance = random.nextDouble();

        if (chance < 0.10) { // 10% xe cứu thương
            return new PriorityVehicle(id, VehicleType.AMBULANCE);
        } else if (chance < 0.40) { // 30% ô tô
            return new StandardVehicle(id, VehicleType.CAR);
        } else { // 60% xe máy
            return new StandardVehicle(id, VehicleType.MOTORBIKE);
        }
    }
}