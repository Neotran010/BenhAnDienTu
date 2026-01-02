package com.benhan.models;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;
    
    private String id;              // Mã số bệnh nhân
    private String name;            // Họ tên
    private String birth;           // Ngày sinh
    private int sex;                // Giới tính (1: Nam, 2: Nữ)
    private String bhyt;            // Số BHYT
    private String bhytFrom;        // BHYT từ ngày
    private String bhytTo;          // BHYT đến ngày
    private String ngayNhapVien;    // Ngày nhập viện
    private String gioNhapVien;     // Giờ nhập viện
    private String lyDoNhapVien;    // Lý do nhập viện
    private String quaTrinhBenhLy;  // Quá trình bệnh lý
    private String tienSuBenh;      // Tiền sử bệnh
    private String vitalSigns;      // Mạch, huyết áp, cân nặng (deprecated, kept for compatibility)
    private String mach;            // Mạch
    private String huyetAp;         // Huyết áp
    private String canNang;         // Cân nặng
    private String chanDoanChinh;   // Chẩn đoán chính
    private String chanDoanPhu;     // Chẩn đoán phụ
    private String bacSiDieuTri;    // Bác sĩ điều trị
    private List<DieuTri> toDieuTri;
    
    public Patient() {
        this.toDieuTri = new ArrayList<>();
    }
    
    public Patient(String id) {
        this.id = id;
        this.toDieuTri = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBirth() {
        return birth;
    }
    
    public void setBirth(String birth) {
        this.birth = birth;
    }
    
    public int getSex() {
        return sex;
    }
    
    public void setSex(int sex) {
        this.sex = sex;
    }
    
    public String getBhyt() {
        return bhyt;
    }
    
    public void setBhyt(String bhyt) {
        this.bhyt = bhyt;
    }
    
    public String getBhytFrom() {
        return bhytFrom;
    }
    
    public void setBhytFrom(String bhytFrom) {
        this.bhytFrom = bhytFrom;
    }
    
    public String getBhytTo() {
        return bhytTo;
    }
    
    public void setBhytTo(String bhytTo) {
        this.bhytTo = bhytTo;
    }
    
    public String getGioNhapVien() {
        return gioNhapVien;
    }
    
    public void setGioNhapVien(String gioNhapVien) {
        this.gioNhapVien = gioNhapVien;
    }
    
    public String getLyDoNhapVien() {
        return lyDoNhapVien;
    }
    
    public void setLyDoNhapVien(String lyDoNhapVien) {
        this.lyDoNhapVien = lyDoNhapVien;
    }
    
    public String getQuaTrinhBenhLy() {
        return quaTrinhBenhLy;
    }
    
    public void setQuaTrinhBenhLy(String quaTrinhBenhLy) {
        this.quaTrinhBenhLy = quaTrinhBenhLy;
    }
    
    public String getTienSuBenh() {
        return tienSuBenh;
    }
    
    public void setTienSuBenh(String tienSuBenh) {
        this.tienSuBenh = tienSuBenh;
    }
    
    public String getVitalSigns() {
        return vitalSigns;
    }
    
    public void setVitalSigns(String vitalSigns) {
        this.vitalSigns = vitalSigns;
    }
    
    public String getChanDoanChinh() {
        return chanDoanChinh;
    }
    
    public void setChanDoanChinh(String chanDoanChinh) {
        this.chanDoanChinh = chanDoanChinh;
    }
    
    public String getChanDoanPhu() {
        return chanDoanPhu;
    }
    
    public void setChanDoanPhu(String chanDoanPhu) {
        this.chanDoanPhu = chanDoanPhu;
    }
    
    public String getBacSiDieuTri() {
        return bacSiDieuTri;
    }
    
    public void setBacSiDieuTri(String bacSiDieuTri) {
        this.bacSiDieuTri = bacSiDieuTri;
    }
    
    public List<DieuTri> getToDieuTri() {
        return toDieuTri;
    }
    
    public void setToDieuTri(List<DieuTri> toDieuTri) {
        this.toDieuTri = toDieuTri;
    }
    
    public String getNgayNhapVien() {
        return ngayNhapVien;
    }
    
    public void setNgayNhapVien(String ngayNhapVien) {
        this.ngayNhapVien = ngayNhapVien;
    }
    
    public String getMach() {
        return mach;
    }
    
    public void setMach(String mach) {
        this.mach = mach;
    }
    
    public String getHuyetAp() {
        return huyetAp;
    }
    
    public void setHuyetAp(String huyetAp) {
        this.huyetAp = huyetAp;
    }
    
    public String getCanNang() {
        return canNang;
    }
    
    public void setCanNang(String canNang) {
        this.canNang = canNang;
    }
}
