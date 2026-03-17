package com.traffic.simulator.util;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
