package com.ra.bt.ex3;

public class Main {
    public static void main(String[] args) {
        HospitalService service = new HospitalService();

        System.out.println("thanh cong ");
        service.xuatVienVaThanhToan(1, 500000);

        System.out.println("\nbay 1: thieu tien");
        service.xuatVienVaThanhToan(2, 900000);

        System.out.println("\nbay 2: ma benh nhan khong ton tai");
        service.xuatVienVaThanhToan(999, 100000);
    }
}
