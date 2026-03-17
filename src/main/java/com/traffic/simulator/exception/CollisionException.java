package com.traffic.simulator.exception;

public class CollisionException extends Exception {
    public CollisionException(String message) {
        super("VA CHẠM: " + message);
    }
}