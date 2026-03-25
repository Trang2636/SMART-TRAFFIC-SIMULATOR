package com.ra.bt.ex2;

public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService();

        System.out.println("thanh toan thanh cong");
        paymentService.thanhToanVienPhi(1, 1, 500000);

        System.out.println("\nthanh toan that bai");
        paymentService.thanhToanVienPhi(1, 999, 200000);
    }
}
