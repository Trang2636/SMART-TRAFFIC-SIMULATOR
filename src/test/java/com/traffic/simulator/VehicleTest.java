import entity.*;
import engine.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    Intersection intersection;

    @BeforeEach
    void setup() {
        intersection = new Intersection();
        // reset trạng thái
        PriorityManager.deactivate();
    }
    @Test
    void testStandardVehicleStopsAtRedLight() {
        StandardVehicle v = new StandardVehicle("Car-1", intersection);

        boolean shouldWait = v.shouldWait();

        assertTrue(shouldWait, "Xe thường phải dừng khi đèn đỏ");
    }
    @Test
    void testPriorityVehicleBypassesRedLight() {
        PriorityVehicle v = new PriorityVehicle("Ambulance-1", intersection);

        boolean shouldWait = v.shouldWait();

        assertFalse(shouldWait, "Xe ưu tiên không được dừng");
    }
    @Test
    void testStandardVehicleYieldsToPriority() {
        // giả lập có xe ưu tiên
        PriorityManager.activate();

        StandardVehicle v = new StandardVehicle("Car-2", intersection);

        boolean shouldWait = v.shouldWait();

        assertTrue(shouldWait, "Xe thường phải nhường đường cho xe ưu tiên");

        PriorityManager.deactivate();
    }
    @Test
    void testPriorityAffectsOtherVehicles() throws InterruptedException {

        Thread normalCar = new Thread(new StandardVehicle("Car-3", intersection));
        Thread ambulance = new Thread(new PriorityVehicle("Ambulance-2", intersection));

        normalCar.start();
        // xe thường chạy trước
        Thread.sleep(500);
        // xe ưu tiên xuất hiện
        ambulance.start();

        normalCar.join();
        ambulance.join();

        assertFalse(PriorityManager.isActive(), "Sau khi xe ưu tiên đi qua, trạng thái phải reset");
    }
}