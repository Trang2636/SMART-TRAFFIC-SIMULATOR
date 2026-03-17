package com.traffic.simulator.pattern.state;

import com.traffic.simulator.pattern.observer.TrafficLight;

public class RedState implements TrafficLightState {
    @Override
    public void handle(TrafficLight trafficLight) {
        trafficLight.setState(new GreenState());
    }
    @Override
    public String getName() {
        return "Den do";
    }
    @Override
    public int getDuration() {
        return 7;
    }
    @Override
    public void next(TrafficLight trafficLight) {
        trafficLight.setState(new GreenState());
    }
}