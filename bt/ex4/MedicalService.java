package com.ra.bt.ex4;
public class MedicalService {
    private int maDichVu;
    private String tenDichVu;
    private String loaiDichVu;

    public MedicalService() {
    }

    public MedicalService(int maDichVu, String tenDichVu, String loaiDichVu) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.loaiDichVu = loaiDichVu;
    }

    public int getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(int maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getLoaiDichVu() {
        return loaiDichVu;
    }

    public void setLoaiDichVu(String loaiDichVu) {
        this.loaiDichVu = loaiDichVu;
    }

    @Override
    public String toString() {
        return "DichVu{" +
                "maDichVu=" + maDichVu +
                ", tenDichVu='" + tenDichVu + '\'' +
                ", loaiDichVu='" + loaiDichVu + '\'' +
                '}';
    }
}
