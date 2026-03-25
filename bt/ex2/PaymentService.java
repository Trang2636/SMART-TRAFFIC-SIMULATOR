package com.ra.bt.ex2;

import com.ra.bt.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentService {
    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DBConnection.openConnection();
            conn.setAutoCommit(false);

            // tru tien trong vi benh nhan
            String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? " +
                    "WHERE patient_id = ? AND balance >= ?";

            ps1 = conn.prepareStatement(sqlDeductWallet);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.setDouble(3, amount);

            int row1 = ps1.executeUpdate();

            // kiem tra vi co ton tai va du tien hay khong
            if (row1 == 0) {
                throw new SQLException("Vi khong ton tai hoac khong du so du");
            }

            // cap nhat trang thai hoa don
            String sqlUpdateInvoice = "UPDATE Invoices SET status = 'PAID' WHERE invoice_id = ?";
            ps2 = conn.prepareStatement(sqlUpdateInvoice);
            ps2.setInt(1, invoiceId);

            int row2 = ps2.executeUpdate();

            // kiem tra hoa don co ton tai hay khong
            if (row2 == 0) {
                throw new SQLException("Hoa don khong ton tai");
            }

            // neu ca 2 thao tac deu thanh cong thi commit
            conn.commit();
            System.out.println("Thanh toan hoan tat");

        } catch (SQLException e) {
            System.out.println("Loi he thong: Khong the hoan tat thanh toan. Chi tiet: " + e.getMessage());

            // rollback de huy toan bo giao dich khi co loi
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback giao dich");
                } catch (SQLException ex) {
                    System.out.println("Rollback that bai: " + ex.getMessage());
                }
            }

        } finally {
            // mo lai auto commit va dong connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Loi khi dong ket noi: " + e.getMessage());
                }
            }
        }
    }
}


// Phần 1: Phân tích
/*
Chương trình đã tắt Auto-Commit để thực hiện Transaction.
Khi xảy ra lỗi, hệ thống chỉ in lỗi mà không gọi rollback(),
làm giao dịch không được hủy rõ ràng và dữ liệu có thể bị sai lệch.

Trong Transaction, nếu có lỗi bắt buộc phải rollback()
để đảm bảo nguyên tắc: hoặc thực hiện hoàn toàn, hoặc hủy toàn bộ.
*/