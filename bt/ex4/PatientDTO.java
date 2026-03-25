package com.ra.bt.ex4;

import java.util.ArrayList;
import java.util.List;

public class PatientDTO {
    private int maBenhNhan;
    private String tenBenhNhan;
    private String ngayNhapVien;
    private List<DichVu> dsDichVu = new ArrayList<>();

    public PatientDTO() {
    }

    public PatientDTO(int maBenhNhan, String tenBenhNhan, String ngayNhapVien) {
        this.maBenhNhan = maBenhNhan;
        this.tenBenhNhan = tenBenhNhan;
        this.ngayNhapVien = ngayNhapVien;
    }

    public int getMaBenhNhan() {
        return maBenhNhan;
    }

    public void setMaBenhNhan(int maBenhNhan) {
        this.maBenhNhan = maBenhNhan;
    }

    public String getTenBenhNhan() {
        return tenBenhNhan;
    }

    public void setTenBenhNhan(String tenBenhNhan) {
        this.tenBenhNhan = tenBenhNhan;
    }

    public String getNgayNhapVien() {
        return ngayNhapVien;
    }

    public void setNgayNhapVien(String ngayNhapVien) {
        this.ngayNhapVien = ngayNhapVien;
    }

    public List<DichVu> getDsDichVu() {
        return dsDichVu;
    }

    public void setDsDichVu(List<DichVu> dsDichVu) {
        this.dsDichVu = dsDichVu;
    }

    @Override
    public String toString() {
        return "BenhNhanDTO{" +
                "maBenhNhan=" + maBenhNhan +
                ", tenBenhNhan='" + tenBenhNhan + '\'' +
                ", ngayNhapVien='" + ngayNhapVien + '\'' +
                ", dsDichVu=" + dsDichVu +
                '}';
    }
}
