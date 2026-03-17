package com.traffic.simulator;

import com.traffic.simulator.engine.Intersection;
import com.traffic.simulator.engine.SimulationEngine;
import com.traffic.simulator.engine.TrafficEnvironment;
import com.traffic.simulator.pattern.factory.VehicleFactory;
import com.traffic.simulator.pattern.observer.TrafficLight;

public class Main {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        Intersection intersection = new Intersection();
        TrafficEnvironment environment = new TrafficEnvironment();
        VehicleFactory vehicleFactory = new VehicleFactory();

        SimulationEngine engine = new SimulationEngine(trafficLight, intersection, environment, vehicleFactory);

        engine.startSimulation(30); // Run for 30 seconds
    }
}
