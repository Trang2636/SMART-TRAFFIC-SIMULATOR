package com.traffic.simulator.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static synchronized void info(String message) {
        System.out.println(String.format("[%s] [INFO] %s", LocalTime.now().format(dtf), message));
    }

    public static synchronized void warning(String message) {
        System.out.println(YELLOW + String.format("[%s] [WARN] %s", LocalTime.now().format(dtf), message) + RESET);
    }

    public static synchronized void error(String message) {
        System.out.println(RED + String.format("[%s] [ERROR] %s", LocalTime.now().format(dtf), message) + RESET);
    }
}