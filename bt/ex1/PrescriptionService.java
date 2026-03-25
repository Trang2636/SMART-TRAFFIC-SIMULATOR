package com.ra.bt.ex1;

import com.ra.bt.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PrescriptionService {
    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        try {
            conn = DBConnection.openConnection();
            conn.setAutoCommit(false);

            // tru thuoc trong kho
            String sql1 = "UPDATE Medicine_Inventory " + "SET quantity = quantity - 1 " + "WHERE medicine_id = ? AND quantity > 0";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, medicineId);

            int row = ps1.executeUpdate();
            if (row == 0) {
                throw new RuntimeException("Thuoc khong ton tai hoac het hang");
            }

            // lich su cap phat
            String sql2 = "INSERT INTO Prescription_History(patient_id, medicine_id, date) " +
                    "VALUES (?, ?, NOW())";

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();
            conn.commit();
            System.out.println("Cap phat thuoc thanh cong");

        } catch (Exception e) {
            System.out.println("Giao dich that bai -> rollback");
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (Exception e) {
            }
        }
    }
}

// Phần 1: Phân tích
/*
Trong JDBC, Auto-Commit mặc định được bật nên mỗi câu lệnh SQL sẽ được commit ngay.
Chương trình trừ thuốc trong kho trước nên dữ liệu đã lưu,
sau đó xảy ra lỗi khiến việc ghi lịch sử cấp phát không thực hiện.

Do hai thao tác không nằm trong cùng một Transaction,
database không thể tự rollback thao tác trước đó.
Kết quả: thuốc bị trừ nhưng không có lịch sử → vi phạm tính nguyên tử (Atomicity).
*/