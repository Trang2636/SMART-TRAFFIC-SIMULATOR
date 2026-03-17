package com.traffic.simulator.pattern.state;

import com.traffic.simulator.pattern.observer.TrafficLight;

public class GreenState implements TrafficLightState {
    @Override
    public void handle(TrafficLight trafficLight) {
        trafficLight.setState(new YellowState());
    }
    @Override
    public String getName() {
        return "Den xanh";
    }
    @Override
    public int getDuration() {
        return 7;
    }
    @Override
    public void next(TrafficLight trafficLight) {
        trafficLight.setState(new YellowState());
    }
}