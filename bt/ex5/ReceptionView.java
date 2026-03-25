package com.ra.bt.ex5;

import java.util.List;
import java.util.Scanner;

public class ReceptionView {
    private final Scanner scanner = new Scanner(System.in);
    private final PatientController controller = new PatientController();

    public void start() {
        while (true) {
            showMenu();
            int choice = inputInt("Chon chuc nang: ");

            switch (choice) {
                case 1:
                    showAvailableBeds();
                    break;
                case 2:
                    admitPatient();
                    break;
                case 3:
                    System.out.println("Thoat chuong trinh");
                    return;
                default:
                    System.out.println("Lua chon khong hop le");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n========== HE THONG TIEP NHAN NOI TRU ==========");
        System.out.println("1. Xem tinh trang giuong benh");
        System.out.println("2. Tiep nhan benh nhan");
        System.out.println("3. Thoat");
    }

    private void showAvailableBeds() {
        List<Bed> beds = controller.getAvailableBeds();

        if (beds.isEmpty()) {
            System.out.println("Khong co giuong trong");
            return;
        }

        System.out.println("Danh sach giuong dang trong:");
        for (Bed bed : beds) {
            System.out.println("Ma giuong: " + bed.getBedId()
                    + " | Ten giuong: " + bed.getBedName()
                    + " | Trang thai: " + bed.getStatus());
        }
    }

    private void admitPatient() {
        System.out.println("=== Nhap thong tin tiep nhan ===");

        String fullName = inputString("Nhap ten benh nhan: ");
        int age = inputInt("Nhap tuoi: ");
        int bedId = inputInt("Nhap ma giuong muon chon: ");
        double advanceAmount = inputDouble("Nhap so tien tam ung: ");

        controller.admitPatient(fullName, age, bedId, advanceAmount);
    }

    private String inputString(String message) {
        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Du lieu khong duoc de trong");
        }
    }

    private int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui long nhap so nguyen hop le");
            }
        }
    }

    private double inputDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui long nhap so hop le");
            }
        }
    }
}