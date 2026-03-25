package com.ra.bt.ex4;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DashboardService service = new DashboardService();
        List<BenhNhanDTO> ds = service.layDanhSachDashboard();

        for (BenhNhanDTO bn : ds) {
            System.out.println("Benh nhan: " + bn.getTenBenhNhan());

            if (bn.getDsDichVu().isEmpty()) {
                System.out.println("  Chua co dich vu nao");
            } else {
                for (DichVu dv : bn.getDsDichVu()) {
                    System.out.println("  - " + dv.getTenDichVu() + " (" + dv.getLoaiDichVu() + ")");
                }
            }
        }
    }
}
