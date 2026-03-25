package com.ra.bt.ex1;

public class Main {
    public static void main(String[] args) {
        PrescriptionService service = new PrescriptionService();
        System.out.println("thanh cong");
        service.capPhatThuoc(1, 101);

        System.out.println("\nrollback");
        service.capPhatThuoc(999, 101);

    }
}
