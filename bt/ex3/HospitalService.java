package com.ra.bt.ex3;

import com.ra.bt.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalService {
    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdateBalance = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psUpdatePatient = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.openConnection();

            if (conn == null) {
                throw new SQLException("Khong the ket noi den database");
            }
            conn.setAutoCommit(false);
            // Lay thong tin benh nhan
            String sqlSelectPatient = "SELECT advance_balance, bed_id FROM patients WHERE patient_id = ?";
            psSelect = conn.prepareStatement(sqlSelectPatient);
            psSelect.setInt(1, maBenhNhan);
            rs = psSelect.executeQuery();

            if (!rs.next()) {
                throw new Exception("Khong tim thay benh nhan");
            }

            double soDuTamUng = rs.getDouble("advance_balance");
            int maGiuong = rs.getInt("bed_id");

            // BAY 1: KIEM TRA SO DU KHONG DU
            // Neu so du nho hon tien vien phi thi khong duoc tru tien
            // Chu dong nem loi de rollback toan bo giao dich
            if (soDuTamUng < tienVienPhi) {
                throw new Exception("So du tam ung khong du de thanh toan vien phi");
            }

            //Tru tien vien phi vao so du tam ung
            String sqlUpdateBalance = "UPDATE patients SET advance_balance = advance_balance - ? WHERE patient_id = ?";
            psUpdateBalance = conn.prepareStatement(sqlUpdateBalance);
            psUpdateBalance.setDouble(1, tienVienPhi);
            psUpdateBalance.setInt(2, maBenhNhan);

            int rowBalance = psUpdateBalance.executeUpdate();

            // BAY 2: KIEM TRA ROW AFFECTED = 0
            // Neu update ma khong co dong nao bi anh huong thi nghia la giao dich vo nghia
            // chu dong nem loi de rollback
            if (rowBalance == 0) {
                throw new Exception("Khong tru duoc tien vien phi cho benh nhan");
            }

            // Giai phong giuong benh
            String sqlUpdateBed = "UPDATE beds SET status = 'TRONG' WHERE bed_id = ?";
            psUpdateBed = conn.prepareStatement(sqlUpdateBed);
            psUpdateBed.setInt(1, maGiuong);

            int rowBed = psUpdateBed.executeUpdate();

            if (rowBed == 0) {
                throw new Exception("Khong cap nhat duoc trang thai giuong benh");
            }

            // Cap nhat trang thai benh nhan da xuat vien
            String sqlUpdatePatient = "UPDATE patients SET status = 'DA_XUAT_VIEN' WHERE patient_id = ?";
            psUpdatePatient = conn.prepareStatement(sqlUpdatePatient);
            psUpdatePatient.setInt(1, maBenhNhan);

            int rowPatient = psUpdatePatient.executeUpdate();

            if (rowPatient == 0) {
                throw new Exception("Khong cap nhat duoc trang thai benh nhan");
            }
            // ca 3 buoc deu thanh cong thi commit
            conn.commit();
            System.out.println("Xuat vien va thanh toan thanh cong");

        } catch (Exception e) {
            System.out.println("That bai: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback giao dich");
                } catch (SQLException ex) {
                    System.out.println("Rollback that bai: " + ex.getMessage());
                }
            }
        } finally {
            // Dong ket noi
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Loi dong Connection");
                }
            }
        }
    }
}

// Báo cáo phân tích

/*
1. Input / Output
Input:
- maBenhNhan: mã bệnh nhân xuất viện
- tienVienPhi: số tiền cần thanh toán

Output:
- Thông báo thành công nếu hoàn tất giao dịch
- Nếu lỗi → rollback và thông báo thất bại

2. Giải pháp
Sử dụng Transaction trong JDBC:
- setAutoCommit(false)
- Thành công → commit()
- Lỗi → rollback()
→ Đảm bảo dữ liệu luôn nhất quán.

3. Các bước xử lý
- Mở kết nối DB và tắt AutoCommit
- Kiểm tra số dư bệnh nhân
- Trừ tiền viện phí
- Cập nhật trạng thái giường và bệnh nhân
- Nếu tất cả query thành công → commit
- Nếu lỗi → rollback
- Đóng kết nối
*/