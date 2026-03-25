package com.ra.bt.ex4;

import com.ra.bt.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardService {

    public List<BenhNhanDTO> layDanhSachDashboard() {
        List<BenhNhanDTO> result = new ArrayList<>();
        Map<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();

        String sql = """
                SELECT bn.maBenhNhan, bn.tenBenhNhan, bn.ngayNhapVien, dv.maDichVu, dv.tenDichVu, dv.loaiDichVu
                FROM BenhNhan bn
                LEFT JOIN DichVuSuDung dv
                    ON bn.maBenhNhan = dv.maBenhNhan
                WHERE bn.trangThai = 'DANG_DIEU_TRI'
                ORDER BY bn.maBenhNhan
                """;

        try (
                Connection conn = DBConnection.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int maBenhNhan = rs.getInt("maBenhNhan");

                BenhNhanDTO benhNhan = mapBenhNhan.get(maBenhNhan);

                if (benhNhan == null) {
                    benhNhan = new BenhNhanDTO();
                    benhNhan.setMaBenhNhan(maBenhNhan);
                    benhNhan.setTenBenhNhan(rs.getString("tenBenhNhan"));
                    benhNhan.setNgayNhapVien(rs.getString("ngayNhapVien"));
                    mapBenhNhan.put(maBenhNhan, benhNhan);
                }

                // Xu ly Bay 2:
                // Neu benh nhan chua co dich vu thi LEFT JOIN van tra ra 1 dong
                // nhung cac cot cua bang DichVuSuDung se la null.
                // Can kiem tra maDichVu khac null moi tao doi tuong DichVu,
                // neu khong se de dsDichVu rong va van hien thi benh nhan.
                Integer maDichVu = rs.getObject("maDichVu", Integer.class);

                if (maDichVu != null) {
                    DichVu dichVu = new DichVu();
                    dichVu.setMaDichVu(maDichVu);
                    dichVu.setTenDichVu(rs.getString("tenDichVu"));
                    dichVu.setLoaiDichVu(rs.getString("loaiDichVu"));

                    benhNhan.getDsDichVu().add(dichVu);
                }
            }

            result = new ArrayList<>(mapBenhNhan.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

// Phân tích Input / Output
/*
Input: lấy danh sách bệnh nhân đang điều trị (có thể kèm từ khóa tìm kiếm)
Output: trả về List<PatientDTO>, mỗi bệnh nhân gồm thông tin cơ bản
        và danh sách dịch vụ đã sử dụng.
Bệnh nhân chưa có dịch vụ vẫn phải hiển thị với danh sách rỗng.
*/


// Giải pháp thiết kế
/*
Có 2 cách xử lý:

1. N+1 Query
   - Query danh sách bệnh nhân trước
   - Sau đó query dịch vụ cho từng bệnh nhân
   → Code đơn giản nhưng hiệu năng thấp khi dữ liệu lớn.

2. LEFT JOIN + gom dữ liệu trong Java
   - Dùng một câu SQL LEFT JOIN lấy toàn bộ dữ liệu
   - Gom các dòng cùng bệnh nhân thành một object trong Java
   → Giảm số lượng query, tăng tốc độ xử lý.

=> Dashboard yêu cầu tốc độ cao nên chọn giải pháp LEFT JOIN.
*/


// Thiết kế xử lý dữ liệu
/*
SQL dùng LEFT JOIN để lấy tất cả bệnh nhân đang điều trị,
kể cả bệnh nhân chưa có dịch vụ.

Trong Java:
- Dùng Map<patientId, PatientDTO> để gom dữ liệu
- Duyệt ResultSet:
    + Nếu chưa có bệnh nhân trong Map → tạo mới DTO
    + Nếu có serviceId → thêm dịch vụ vào danh sách
- Cuối cùng trả về List từ Map.values()
*/