package com.traffic.simulator.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        System.out.println("[" + LocalDateTime.now().format(formatter) + "] " + message);
    }
}
