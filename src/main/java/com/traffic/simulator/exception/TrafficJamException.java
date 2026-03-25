package com.traffic.simulator.exception;

public class TrafficJamException extends Exception {
    public TrafficJamException(String message) {
        super("KẸT XE: " + message);
    }
}