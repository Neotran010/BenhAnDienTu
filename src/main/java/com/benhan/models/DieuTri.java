package com.benhan.models;

public class DieuTri {
    private int id;
    private String patientId;
    private String date;            // Date (YYYY-MM-DD)
    private String timestamp;       // Timestamp (YYYY-MM-DD HH:MM:SS)
    private String dienTien;        // Diễn tiến
    private String yLenh;           // Y lệnh
    
    public DieuTri() {
    }
    
    public DieuTri(String patientId, String date, String timestamp) {
        this.patientId = patientId;
        this.date = date;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getDienTien() {
        return dienTien;
    }
    
    public void setDienTien(String dienTien) {
        this.dienTien = dienTien;
    }
    
    public String getYLenh() {
        return yLenh;
    }
    
    public void setYLenh(String yLenh) {
        this.yLenh = yLenh;
    }
    
    public int getMonth() {
        if (date != null && date.length() >= 7) {
            try {
                return Integer.parseInt(date.substring(5, 7));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                return 0;
            }
        }
        return 0;
    }
}
