package com.benhan.database;

import com.benhan.models.Patient;
import com.benhan.models.DieuTri;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:benhan.db";
    private Connection connection;
    
    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createTables() {
        String patientTable = "CREATE TABLE IF NOT EXISTS patients (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT," +
                "birth TEXT," +
                "sex INTEGER," +
                "bhyt TEXT," +
                "bhytFrom TEXT," +
                "bhytTo TEXT," +
                "gioNhapVien TEXT," +
                "lyDoNhapVien TEXT," +
                "quaTrinhBenhLy TEXT," +
                "tienSuBenh TEXT," +
                "vitalSigns TEXT," +
                "chanDoanChinh TEXT," +
                "chanDoanPhu TEXT," +
                "bacSiDieuTri TEXT" +
                ")";
        
        String dieuTriTable = "CREATE TABLE IF NOT EXISTS dieu_tri (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "patientId TEXT," +
                "date TEXT," +
                "timestamp TEXT," +
                "dienTien TEXT," +
                "yLenh TEXT," +
                "FOREIGN KEY(patientId) REFERENCES patients(id)" +
                ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(patientTable);
            stmt.execute(dieuTriTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Patient findPatientById(String id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getString("id"));
                patient.setName(rs.getString("name"));
                patient.setBirth(rs.getString("birth"));
                patient.setSex(rs.getInt("sex"));
                patient.setBhyt(rs.getString("bhyt"));
                patient.setBhytFrom(rs.getString("bhytFrom"));
                patient.setBhytTo(rs.getString("bhytTo"));
                patient.setGioNhapVien(rs.getString("gioNhapVien"));
                patient.setLyDoNhapVien(rs.getString("lyDoNhapVien"));
                patient.setQuaTrinhBenhLy(rs.getString("quaTrinhBenhLy"));
                patient.setTienSuBenh(rs.getString("tienSuBenh"));
                patient.setVitalSigns(rs.getString("vitalSigns"));
                patient.setChanDoanChinh(rs.getString("chanDoanChinh"));
                patient.setChanDoanPhu(rs.getString("chanDoanPhu"));
                patient.setBacSiDieuTri(rs.getString("bacSiDieuTri"));
                
                patient.setToDieuTri(getDieuTriByPatientId(id));
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean savePatient(Patient patient) {
        Patient existing = findPatientById(patient.getId());
        
        if (existing == null) {
            return insertPatient(patient);
        } else {
            return updatePatient(patient);
        }
    }
    
    private boolean insertPatient(Patient patient) {
        String sql = "INSERT INTO patients (id, name, birth, sex, bhyt, bhytFrom, bhytTo, " +
                "gioNhapVien, lyDoNhapVien, quaTrinhBenhLy, tienSuBenh, vitalSigns, " +
                "chanDoanChinh, chanDoanPhu, bacSiDieuTri) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient.getId());
            pstmt.setString(2, patient.getName());
            pstmt.setString(3, patient.getBirth());
            pstmt.setInt(4, patient.getSex());
            pstmt.setString(5, patient.getBhyt());
            pstmt.setString(6, patient.getBhytFrom());
            pstmt.setString(7, patient.getBhytTo());
            pstmt.setString(8, patient.getGioNhapVien());
            pstmt.setString(9, patient.getLyDoNhapVien());
            pstmt.setString(10, patient.getQuaTrinhBenhLy());
            pstmt.setString(11, patient.getTienSuBenh());
            pstmt.setString(12, patient.getVitalSigns());
            pstmt.setString(13, patient.getChanDoanChinh());
            pstmt.setString(14, patient.getChanDoanPhu());
            pstmt.setString(15, patient.getBacSiDieuTri());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET name=?, birth=?, sex=?, bhyt=?, bhytFrom=?, bhytTo=?, " +
                "gioNhapVien=?, lyDoNhapVien=?, quaTrinhBenhLy=?, tienSuBenh=?, vitalSigns=?, " +
                "chanDoanChinh=?, chanDoanPhu=?, bacSiDieuTri=? WHERE id=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getBirth());
            pstmt.setInt(3, patient.getSex());
            pstmt.setString(4, patient.getBhyt());
            pstmt.setString(5, patient.getBhytFrom());
            pstmt.setString(6, patient.getBhytTo());
            pstmt.setString(7, patient.getGioNhapVien());
            pstmt.setString(8, patient.getLyDoNhapVien());
            pstmt.setString(9, patient.getQuaTrinhBenhLy());
            pstmt.setString(10, patient.getTienSuBenh());
            pstmt.setString(11, patient.getVitalSigns());
            pstmt.setString(12, patient.getChanDoanChinh());
            pstmt.setString(13, patient.getChanDoanPhu());
            pstmt.setString(14, patient.getBacSiDieuTri());
            pstmt.setString(15, patient.getId());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<DieuTri> getDieuTriByPatientId(String patientId) {
        List<DieuTri> list = new ArrayList<>();
        String sql = "SELECT * FROM dieu_tri WHERE patientId = ? ORDER BY timestamp";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DieuTri dt = new DieuTri();
                dt.setId(rs.getInt("id"));
                dt.setPatientId(rs.getString("patientId"));
                dt.setDate(rs.getString("date"));
                dt.setTimestamp(rs.getString("timestamp"));
                dt.setDienTien(rs.getString("dienTien"));
                dt.setYLenh(rs.getString("yLenh"));
                list.add(dt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean saveDieuTri(DieuTri dieuTri) {
        if (dieuTri.getId() > 0) {
            return updateDieuTri(dieuTri);
        } else {
            return insertDieuTri(dieuTri);
        }
    }
    
    private boolean insertDieuTri(DieuTri dieuTri) {
        String sql = "INSERT INTO dieu_tri (patientId, date, timestamp, dienTien, yLenh) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dieuTri.getPatientId());
            pstmt.setString(2, dieuTri.getDate());
            pstmt.setString(3, dieuTri.getTimestamp());
            pstmt.setString(4, dieuTri.getDienTien());
            pstmt.setString(5, dieuTri.getYLenh());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean updateDieuTri(DieuTri dieuTri) {
        String sql = "UPDATE dieu_tri SET patientId=?, date=?, timestamp=?, dienTien=?, yLenh=? WHERE id=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dieuTri.getPatientId());
            pstmt.setString(2, dieuTri.getDate());
            pstmt.setString(3, dieuTri.getTimestamp());
            pstmt.setString(4, dieuTri.getDienTien());
            pstmt.setString(5, dieuTri.getYLenh());
            pstmt.setInt(6, dieuTri.getId());
            
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
