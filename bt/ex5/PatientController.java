package com.ra.bt.ex5;

import com.ra.bt.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    public List<Bed> getAvailableBeds() {
        List<Bed> beds = new ArrayList<>();
        String sql = "SELECT * FROM beds WHERE status = 'TRONG'";
        try (
                Connection conn = DBConnection.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Bed bed = new Bed(
                        rs.getInt("bed_id"),
                        rs.getString("bed_name"),
                        rs.getString("status")
                );
                beds.add(bed);
            }
        } catch (Exception e) {
            System.out.println("Loi khi lay danh sach giuong trong");
            e.printStackTrace();
        }

        return beds;
    }

    public void admitPatient(String fullName, int age, int bedId, double advanceAmount) {
        Connection conn = null;
        PreparedStatement psCheckBed = null;
        PreparedStatement psInsertPatient = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psInsertFinance = null;
        ResultSet rs = null;
        try {
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new Exception("Ten benh nhan khong duoc de trong");
            }
            if (age <= 0) {
                throw new Exception("Tuoi phai lon hon 0");
            }
            if (advanceAmount <= 0) {
                throw new Exception("So tien tam ung phai lon hon 0");
            }
            conn = DBConnection.openConnection();
            if (conn == null) {
                throw new Exception("Khong the ket noi database");
            }

            // bat dau transaction
            conn.setAutoCommit(false);
            // kiem tra giuong co ton tai va dang trong hay khong
            String sqlCheckBed = "SELECT * FROM beds WHERE bed_id = ? AND status = 'TRONG'";
            psCheckBed = conn.prepareStatement(sqlCheckBed);
            psCheckBed.setInt(1, bedId);
            rs = psCheckBed.executeQuery();
            if (!rs.next()) {
                throw new Exception("Giuong khong ton tai hoac da co nguoi");
            }

            // them moi benh nhan
            String sqlInsertPatient = """
                    INSERT INTO patients(full_name, age, bed_id, created_at)
                    VALUES (?, ?, ?, NOW())
                    """;
            psInsertPatient = conn.prepareStatement(sqlInsertPatient, Statement.RETURN_GENERATED_KEYS);
            psInsertPatient.setString(1, fullName);
            psInsertPatient.setInt(2, age);
            psInsertPatient.setInt(3, bedId);

            int patientRow = psInsertPatient.executeUpdate();
            if (patientRow == 0) {
                throw new Exception("Khong the tao ho so benh nhan");
            }

            int patientId;
            try (ResultSet generatedKeys = psInsertPatient.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patientId = generatedKeys.getInt(1);
                } else {
                    throw new Exception("Khong lay duoc ma benh nhan vua tao");
                }
            }

            // cap nhat trang thai giuong
            String sqlUpdateBed = "UPDATE beds SET status = 'DA_CO_NGUOI' WHERE bed_id = ?";
            psUpdateBed = conn.prepareStatement(sqlUpdateBed);
            psUpdateBed.setInt(1, bedId);
            int bedRow = psUpdateBed.executeUpdate();
            if (bedRow == 0) {
                throw new Exception("Khong cap nhat duoc trang thai giuong");
            }
            // them phieu thu tam ung
            String sqlInsertFinance = """
                    INSERT INTO finance_records(patient_id, amount, created_at)
                    VALUES (?, ?, NOW())
                    """;
            psInsertFinance = conn.prepareStatement(sqlInsertFinance);
            psInsertFinance.setInt(1, patientId);
            psInsertFinance.setDouble(2, advanceAmount);

            int financeRow = psInsertFinance.executeUpdate();
            if (financeRow == 0) {
                throw new Exception("Khong tao duoc phieu thu tam ung");
            }

            conn.commit();
            System.out.println("Tiep nhan benh nhan thanh cong");

        } catch (Exception e) {
            System.out.println("Tiep nhan that bai: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback du lieu an toan");
                } catch (SQLException ex) {
                    System.out.println("Rollback that bai: " + ex.getMessage());
                }
            }

        } finally {
            try {
                if (rs != null) rs.close();
                if (psInsertFinance != null) psInsertFinance.close();
                if (psUpdateBed != null) psUpdateBed.close();
                if (psInsertPatient != null) psInsertPatient.close();
                if (psCheckBed != null) psCheckBed.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Loi khi dong tai nguyen");
            }
        }
    }
}
