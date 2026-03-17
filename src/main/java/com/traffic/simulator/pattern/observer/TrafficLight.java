package com.traffic.simulator.pattern.observer;

import com.traffic.simulator.pattern.state.*;
import com.traffic.simulator.util.LoggerUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
public class TrafficLight implements Subject, Runnable {
    private TrafficLightState currentState;
    private final List<Observer> observers;
    private volatile boolean running = true;
    public TrafficLight() {
        this.currentState = new RedState();
        this.observers = new CopyOnWriteArrayList<>();
    }

    public void setState(TrafficLightState state) {
        this.currentState = state;
        LoggerUtil.log("Den duoc chuyen sang: " + state.getName());
        notifyObservers();
    }

    public String getState() {
        return currentState.getName();
    }

    public void nextState() {
        currentState.next(this);
    }

    public String getCurrentStateName() {
        return currentState.getName();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(currentState.getName());
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(currentState.getDuration() * 3000L);
                currentState.handle(this);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void stop() {
        running = false;
    }
}