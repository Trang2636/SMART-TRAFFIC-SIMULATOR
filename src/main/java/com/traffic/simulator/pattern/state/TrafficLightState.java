package com.traffic.simulator.pattern.state;

import com.traffic.simulator.pattern.observer.TrafficLight;

public interface TrafficLightState {
    void handle(TrafficLight trafficLight);
    String getName();
    int getDuration();
    void next(TrafficLight trafficLight);
}