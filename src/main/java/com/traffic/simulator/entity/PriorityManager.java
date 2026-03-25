package engine;

import java.util.concurrent.atomic.AtomicBoolean;

public class PriorityManager {
    private static final AtomicBoolean priorityActive = new AtomicBoolean(false);

    public static void activate() {
        priorityActive.set(true);
        System.out.println(" Chế độ ưu tiên đã kích hoạt");
    }

    public static void deactivate() {
        priorityActive.set(false);
        System.out.println("Priority mode đã tắt");
    }

    public static boolean isActive() {
        return priorityActive.get();
    }
}