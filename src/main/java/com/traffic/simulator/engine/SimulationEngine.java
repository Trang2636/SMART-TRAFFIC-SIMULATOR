package com.traffic.simulator.engine;

import com.traffic.simulator.entity.PriorityVehicle;
import com.traffic.simulator.entity.Vehicle;
import com.traffic.simulator.pattern.factory.VehicleFactory;
import com.traffic.simulator.pattern.observer.TrafficLight;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationEngine {

    private TrafficLight trafficLight;
    private Intersection intersection;
    private TrafficEnvironment environment;
    private VehicleFactory vehicleFactory;
    private TrafficMonitor monitor;

    private ExecutorService vehicleExecutor;
    private ScheduledExecutorService scheduler;

    private AtomicBoolean running = new AtomicBoolean(false);

    private AtomicInteger totalGenerated = new AtomicInteger(0);
    private AtomicInteger totalPassed = new AtomicInteger(0);

    // Constructor: khởi tạo các thành phần chính của hệ thống
    public SimulationEngine(TrafficLight trafficLight, Intersection intersection, TrafficEnvironment environment, VehicleFactory vehicleFactory) {
        this.trafficLight = trafficLight;
        this.intersection = intersection;
        this.environment = environment;
        this.vehicleFactory = vehicleFactory;
        this.vehicleExecutor = Executors.newFixedThreadPool(10);
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.monitor = new TrafficMonitor(environment);
    }

    // Bắt đầu mô phỏng trong khoảng thời gian (seconds)
    public void startSimulation(int seconds) {
        if (running.get()) {
            System.out.println("Mô phỏng đang chạy rồi.");
            return;
        }
        running.set(true);

        System.out.println("===== BẮT ĐẦU MÔ PHỎNG GIAO THÔNG =====");
        registerShutdownHook();   // đăng ký tắt an toàn
        startTrafficLight();     // chạy đèn giao thông
        startGenerateVehicle();  // sinh xe liên tục
        startMonitoring();       // theo dõi hệ thống
        monitor.start();         // chạy monitor
        // tự động dừng sau thời gian quy định
        scheduler.schedule(() -> stopSimulation(), seconds, TimeUnit.SECONDS);
    }

    // Dừng hệ thống an toàn
    public void stopSimulation() {
        if (!running.get()) return;
        running.set(false);
        System.out.println("Đang tắt hệ thống an toàn...");
        scheduler.shutdown();
        vehicleExecutor.shutdown();
        monitor.stop();
        try {
            if (!vehicleExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Buộc dừng các luồng xe...");
                vehicleExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            vehicleExecutor.shutdownNow();
        }
        printSummary(); // in báo cáo
        System.out.println("===== KẾT THÚC MÔ PHỎNG =====");
    }

    // Điều khiển đèn giao thông chuyển trạng thái theo chu kỳ
    private void startTrafficLight() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!running.get()) return;
            trafficLight.nextState();
            System.out.println("Đèn chuyển sang: " + trafficLight.getCurrentStateName());
        }, 0, 4, TimeUnit.SECONDS);
    }

    // Sinh xe ngẫu nhiên theo thời gian
    private void startGenerateVehicle() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!running.get()) return;
            Vehicle v = vehicleFactory.createRandomVehicle();
            totalGenerated.incrementAndGet();
            environment.addVehicle(v);
            System.out.println("Sinh xe: " + v.getType() + " #" + v.getId());
            vehicleExecutor.submit(() -> processVehicle(v));

        }, 0, 2, TimeUnit.SECONDS);
    }

    // Theo dõi trạng thái hệ thống (log định kỳ)
    private void startMonitoring() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!running.get()) return;
            System.out.println("Theo dõi | Xe chờ: "
                    + environment.getWaitingVehiclesCount()
                    + " | Đã tạo: " + totalGenerated.get()
                    + " | Đã qua: " + totalPassed.get());

        }, 1, 3, TimeUnit.SECONDS);
    }

    // Xử lý logic di chuyển của từng xe
    private void processVehicle(Vehicle v) {
        try {
            System.out.println(v.getType() + " #" + v.getId() + " đang tới ngã tư...");
            Thread.sleep(1000);
            waitTrafficLight(v); // chờ đèn

            // thử vào giao lộ (có lock)
            if (intersection.tryEnter(v, 2, TimeUnit.SECONDS)) {
                System.out.println(v.getType() + " #" + v.getId() + " đang qua ngã tư...");
                Thread.sleep(1000);
                totalPassed.incrementAndGet();
                monitor.vehiclePassed(v);
                environment.removeVehicle(v);
                intersection.leave(v);
                System.out.println("Xe " + v.getId() + " đã qua.");

            } else {
                System.out.println("Xe " + v.getId() + " không vào được giao lộ.");
            }
        } catch (Exception e) {
            System.out.println("Lỗi xe: " + e.getMessage());
        }
    }

    // Xe chờ đèn xanh (trừ xe ưu tiên)
    private void waitTrafficLight(Vehicle v) throws InterruptedException {
        while (running.get()) {
            if (v instanceof PriorityVehicle) {
                System.out.println("Xe ưu tiên " + v.getId() + " được phép đi ngay.");
                return;
            }
            if (trafficLight.getCurrentStateName().equals("GREEN"))
                return;
            System.out.println("Xe " + v.getId() + " đang chờ đèn đỏ...");
            Thread.sleep(500);
        }
    }

    // In báo cáo thống kê cuối
    private void printSummary() {
        System.out.println("===== BÁO CÁO =====");
        System.out.println("Tổng xe tạo: " + totalGenerated.get());
        System.out.println("Tổng xe qua: " + totalPassed.get());
        System.out.println("===================");
    }

    // Đăng ký shutdown hook để tắt an toàn khi chương trình bị dừng
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (running.get()) {
                System.out.println("Phát hiện tắt chương trình, đang shutdown an toàn.");
                stopSimulation();
            }
        }));
    }
}
