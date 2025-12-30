package com.benhan.ui;

import com.benhan.database.DatabaseManager;
import com.benhan.models.Patient;
import com.benhan.models.DieuTri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Frame2 extends JFrame {
    // Constants for doctor names
    private static final String DEFAULT_DOCTOR = "NGUYỄN BÁ ĐỊNH";
    private static final String DOCTOR_1 = "NGÔ ĐẰNG";
    private static final String DOCTOR_2 = "POA DAM THƯƠNG";
    private static final String DOCTOR_3 = "NGUYỄN TRƯỜNG DUY";
    
    private DatabaseManager dbManager;
    private Patient patient;
    private boolean isNewPatient;
    
    private JTextField txtId, txtName, txtBirth, txtBhyt, txtBhytFrom, txtBhytTo;
    private JTextField txtNgayNhapVien, txtGioNhapVienHour, txtGioNhapVienMinute;
    private JTextField txtBacSiDieuTri, txtMach, txtHuyetAp, txtCanNang;
    private JComboBox<String> cmbSex;
    private JTextArea txtLyDoNhapVien, txtQuaTrinhBenhLy, txtTienSuBenh;
    private JTextArea txtChanDoanChinh, txtChanDoanPhu;
    private JButton btnSave, btnCancel, btnAddTreatment;
    private JPanel monthButtonPanel;
    
    public Frame2(DatabaseManager dbManager, Patient patient, boolean isNewPatient) {
        this.dbManager = dbManager;
        this.patient = patient;
        this.isNewPatient = isNewPatient;
        initComponents();
        loadPatientData();
        createMonthButtons();
    }
    
    private void initComponents() {
        setTitle("THÔNG TIN BỆNH ÁN");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Patient info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Row 1: ID and Name
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Mã số BN:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        txtId = new JTextField(patient.getId());
        txtId.setEditable(false);
        infoPanel.add(txtId, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0;
        infoPanel.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.7;
        txtName = new JTextField();
        infoPanel.add(txtName, gbc);
        
        row++;
        
        // Row 2: Birth and Sex
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        txtBirth = new JTextField();
        addDateFormatting(txtBirth);
        infoPanel.add(txtBirth, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0;
        infoPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.7;
        cmbSex = new JComboBox<>(new String[]{"Nam", "Nữ"});
        infoPanel.add(cmbSex, gbc);
        
        row++;
        
        // Row 3: BHYT
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Số BHYT:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        txtBhyt = new JTextField();
        infoPanel.add(txtBhyt, gbc);
        
        gbc.gridx = 2; gbc.weightx = 0;
        infoPanel.add(new JLabel("Từ:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        JPanel bhytPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        txtBhytFrom = new JTextField();
        addDateFormatting(txtBhytFrom);
        bhytPanel.add(txtBhytFrom);
        bhytPanel.add(new JLabel("Đến:"));
        txtBhytTo = new JTextField();
        addDateFormatting(txtBhytTo);
        bhytPanel.add(txtBhytTo);
        infoPanel.add(bhytPanel, gbc);
        
        row++;
        
        // Row 4: Ngay Nhap Vien (NEW)
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Ngày nhập viện:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3;
        txtNgayNhapVien = new JTextField();
        addDateFormatting(txtNgayNhapVien);
        infoPanel.add(txtNgayNhapVien, gbc);
        
        // Row 4: Admission time (split into hour and minute)
        gbc.gridx = 2; gbc.weightx = 0;
        infoPanel.add(new JLabel("Giờ nhập viện:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        txtGioNhapVienHour = new JTextField(3);
        txtGioNhapVienMinute = new JTextField(3);
        addTimeValidation(txtGioNhapVienHour, 0, 23);
        addTimeValidation(txtGioNhapVienMinute, 0, 59);
        timePanel.add(txtGioNhapVienHour);
        timePanel.add(new JLabel("giờ"));
        timePanel.add(txtGioNhapVienMinute);
        timePanel.add(new JLabel("phút"));
        infoPanel.add(timePanel, gbc);
        
        row++;
        
        // Row 5: Admission reason (reduced size)
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Lý do nhập viện:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtLyDoNhapVien = new JTextArea(1, 20);
        txtLyDoNhapVien.setLineWrap(true);
        txtLyDoNhapVien.setWrapStyleWord(true);
        JScrollPane scrollLyDo = new JScrollPane(txtLyDoNhapVien);
        scrollLyDo.setPreferredSize(new Dimension(0, 35));
        infoPanel.add(scrollLyDo, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 6: Medical history
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Quá trình bệnh lý:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtQuaTrinhBenhLy = new JTextArea(2, 20);
        txtQuaTrinhBenhLy.setLineWrap(true);
        txtQuaTrinhBenhLy.setWrapStyleWord(true);
        JScrollPane scrollQuaTrinh = new JScrollPane(txtQuaTrinhBenhLy);
        infoPanel.add(scrollQuaTrinh, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 7: Past illness
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Tiền sử bệnh:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtTienSuBenh = new JTextArea(2, 20);
        txtTienSuBenh.setLineWrap(true);
        txtTienSuBenh.setWrapStyleWord(true);
        JScrollPane scrollTienSu = new JScrollPane(txtTienSuBenh);
        infoPanel.add(scrollTienSu, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 8: Vital signs (split into three fields)
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Sinh hiệu:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        JPanel vitalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        vitalPanel.add(new JLabel("Mạch:"));
        txtMach = new JTextField(6);
        vitalPanel.add(txtMach);
        vitalPanel.add(new JLabel("Huyết áp:"));
        txtHuyetAp = new JTextField(6);
        vitalPanel.add(txtHuyetAp);
        vitalPanel.add(new JLabel("Cân nặng:"));
        txtCanNang = new JTextField(6);
        vitalPanel.add(txtCanNang);
        infoPanel.add(vitalPanel, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 9: Main diagnosis
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Chẩn đoán chính:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtChanDoanChinh = new JTextArea(2, 20);
        txtChanDoanChinh.setLineWrap(true);
        txtChanDoanChinh.setWrapStyleWord(true);
        JScrollPane scrollChanDoan = new JScrollPane(txtChanDoanChinh);
        infoPanel.add(scrollChanDoan, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 10: Additional diagnosis
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Chẩn đoán phụ:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtChanDoanPhu = new JTextArea(2, 20);
        txtChanDoanPhu.setLineWrap(true);
        txtChanDoanPhu.setWrapStyleWord(true);
        JScrollPane scrollChanDoanPhu = new JScrollPane(txtChanDoanPhu);
        infoPanel.add(scrollChanDoanPhu, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 11: Doctor with shortcuts
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Bác sĩ điều trị:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtBacSiDieuTri = new JTextField();
        addDoctorShortcuts(txtBacSiDieuTri);
        infoPanel.add(txtBacSiDieuTri, gbc);
        
        row++;
        
        // Row 12: Add Treatment Button
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel(""), gbc);
        gbc.gridx = 1; gbc.weightx = 0; gbc.gridwidth = 3;
        btnAddTreatment = new JButton("Thêm Hồ Sơ Điều Trị");
        btnAddTreatment.setFont(new Font("Arial", Font.BOLD, 12));
        btnAddTreatment.addActionListener(e -> addNewTreatmentRecord());
        btnAddTreatment.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addNewTreatmentRecord();
                }
            }
        });
        infoPanel.add(btnAddTreatment, gbc);
        
        JScrollPane scrollInfo = new JScrollPane(infoPanel);
        scrollInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        mainPanel.add(scrollInfo, BorderLayout.CENTER);
        
        // Button panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Save/Cancel buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.addActionListener(e -> savePatient());
        
        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.addActionListener(e -> dispose());
        
        actionPanel.add(btnSave);
        actionPanel.add(btnCancel);
        bottomPanel.add(actionPanel, BorderLayout.NORTH);
        
        // Month buttons panel
        monthButtonPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        monthButtonPanel.setBorder(BorderFactory.createTitledBorder("Hồ sơ điều trị theo tháng"));
        bottomPanel.add(monthButtonPanel, BorderLayout.CENTER);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void addDateFormatting(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = field.getText().trim();
                if (!text.isEmpty()) {
                    String formatted = formatDate(text);
                    field.setText(formatted);
                }
            }
        });
    }
    
    private String formatDate(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        
        input = input.trim();
        
        // Already in correct format
        if (input.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return input;
        }
        
        // Handle year only: "2025" -> "01/01/2025"
        if (input.matches("\\d{4}")) {
            return "01/01/" + input;
        }
        
        // Handle various date formats
        String[] parts = input.split("[/-]");
        
        if (parts.length == 3) {
            try {
                int day, month, year;
                
                // Check if format is yyyy-mm-dd or yyyy/mm/dd
                if (parts[0].length() == 4) {
                    year = Integer.parseInt(parts[0]);
                    month = Integer.parseInt(parts[1]);
                    day = Integer.parseInt(parts[2]);
                } else {
                    // Assume dd/mm/yyyy or dd-mm-yyyy
                    day = Integer.parseInt(parts[0]);
                    month = Integer.parseInt(parts[1]);
                    year = Integer.parseInt(parts[2]);
                    
                    // Handle 2-digit year
                    if (year < 100) {
                        year += (year < 50) ? 2000 : 1900;
                    }
                }
                
                return String.format("%02d/%02d/%04d", day, month, year);
            } catch (NumberFormatException e) {
                return input;
            }
        } else if (parts.length == 2) {
            // Handle mm/yyyy -> 01/mm/yyyy
            try {
                int month = Integer.parseInt(parts[0]);
                int year = Integer.parseInt(parts[1]);
                if (year < 100) {
                    year += (year < 50) ? 2000 : 1900;
                }
                return String.format("01/%02d/%04d", month, year);
            } catch (NumberFormatException e) {
                return input;
            }
        }
        
        return input;
    }
    
    private void addTimeValidation(JTextField field, int min, int max) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = field.getText().trim();
                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        if (value < min || value > max) {
                            JOptionPane.showMessageDialog(Frame2.this,
                                "Giá trị phải từ " + min + " đến " + max,
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                            field.setText("");
                        } else {
                            field.setText(String.format("%02d", value));
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Frame2.this,
                            "Giá trị phải là số",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                        field.setText("");
                    }
                }
            }
        });
    }
    
    private void addDoctorShortcuts(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = field.getText().trim();
                
                // Only apply shortcuts if it's a single digit
                if (text.length() == 1 && Character.isDigit(text.charAt(0))) {
                    switch (text) {
                        case "1":
                            field.setText(DOCTOR_1);
                            break;
                        case "2":
                            field.setText(DOCTOR_2);
                            break;
                        case "3":
                            field.setText(DOCTOR_3);
                            break;
                        case "0":
                            field.setText(DEFAULT_DOCTOR);
                            break;
                    }
                }
            }
        });
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = field.getText().trim();
                // If empty on focus lost, set default
                if (text.isEmpty()) {
                    field.setText(DEFAULT_DOCTOR);
                }
            }
        });
    }
    
    private void loadPatientData() {
        if (patient.getName() != null) {
            txtName.setText(patient.getName());
            txtBirth.setText(patient.getBirth() != null ? patient.getBirth() : "");
            cmbSex.setSelectedIndex(patient.getSex() == Patient.SEX_MALE ? 0 : 1);
            txtBhyt.setText(patient.getBhyt() != null ? patient.getBhyt() : "");
            txtBhytFrom.setText(patient.getBhytFrom() != null ? patient.getBhytFrom() : "");
            txtBhytTo.setText(patient.getBhytTo() != null ? patient.getBhytTo() : "");
            txtNgayNhapVien.setText(patient.getNgayNhapVien() != null ? patient.getNgayNhapVien() : "");
            
            // Parse time from "HH giờ mm phút" format
            String gioNhapVien = patient.getGioNhapVien();
            if (gioNhapVien != null && !gioNhapVien.isEmpty()) {
                // Parse format: "HH giờ mm phút"
                int gioIndex = gioNhapVien.indexOf(" giờ");
                int phutIndex = gioNhapVien.indexOf(" phút");
                
                if (gioIndex > 0) {
                    String hour = gioNhapVien.substring(0, gioIndex).trim();
                    txtGioNhapVienHour.setText(hour);
                    
                    if (phutIndex > gioIndex) {
                        String minute = gioNhapVien.substring(gioIndex + 5, phutIndex).trim();
                        txtGioNhapVienMinute.setText(minute);
                    }
                }
            }
            
            txtLyDoNhapVien.setText(patient.getLyDoNhapVien() != null ? patient.getLyDoNhapVien() : "");
            txtQuaTrinhBenhLy.setText(patient.getQuaTrinhBenhLy() != null ? patient.getQuaTrinhBenhLy() : "");
            txtTienSuBenh.setText(patient.getTienSuBenh() != null ? patient.getTienSuBenh() : "");
            
            // Load vital signs from new fields or parse from old format
            if (patient.getMach() != null || patient.getHuyetAp() != null || patient.getCanNang() != null) {
                txtMach.setText(patient.getMach() != null ? patient.getMach() : "");
                txtHuyetAp.setText(patient.getHuyetAp() != null ? patient.getHuyetAp() : "");
                txtCanNang.setText(patient.getCanNang() != null ? patient.getCanNang() : "");
            } else if (patient.getVitalSigns() != null && !patient.getVitalSigns().isEmpty()) {
                // Try to parse old format
                String[] vitalParts = patient.getVitalSigns().split(",");
                if (vitalParts.length >= 1) txtMach.setText(vitalParts[0].trim());
                if (vitalParts.length >= 2) txtHuyetAp.setText(vitalParts[1].trim());
                if (vitalParts.length >= 3) txtCanNang.setText(vitalParts[2].trim());
            }
            
            txtChanDoanChinh.setText(patient.getChanDoanChinh() != null ? patient.getChanDoanChinh() : "");
            txtChanDoanPhu.setText(patient.getChanDoanPhu() != null ? patient.getChanDoanPhu() : "");
            txtBacSiDieuTri.setText(patient.getBacSiDieuTri() != null ? patient.getBacSiDieuTri() : "");
        }
    }
    
    private void createMonthButtons() {
        Set<Integer> availableMonths = new HashSet<>();
        if (patient.getToDieuTri() != null) {
            for (DieuTri dt : patient.getToDieuTri()) {
                availableMonths.add(dt.getMonth());
            }
        }
        
        for (int i = 1; i <= 12; i++) {
            final int month = i;
            JButton btnMonth = new JButton("Tháng " + i);
            btnMonth.setFont(new Font("Arial", Font.PLAIN, 12));
            
            if (availableMonths.contains(i)) {
                btnMonth.setEnabled(true);
                btnMonth.addActionListener(e -> openMonthRecords(month));
            } else {
                btnMonth.setEnabled(false);
            }
            
            monthButtonPanel.add(btnMonth);
        }
    }
    
    private boolean validateDate(String date) {
        if (date == null || date.isEmpty()) {
            return true; // Empty is valid
        }
        
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    private void savePatient() {
        // Validate date fields
        if (!validateDate(txtBirth.getText().trim())) {
            JOptionPane.showMessageDialog(this,
                "Ngày sinh không hợp lệ! Định dạng: dd/MM/yyyy",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validateDate(txtBhytFrom.getText().trim())) {
            JOptionPane.showMessageDialog(this,
                "BHYT từ ngày không hợp lệ! Định dạng: dd/MM/yyyy",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validateDate(txtBhytTo.getText().trim())) {
            JOptionPane.showMessageDialog(this,
                "BHYT đến ngày không hợp lệ! Định dạng: dd/MM/yyyy",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!validateDate(txtNgayNhapVien.getText().trim())) {
            JOptionPane.showMessageDialog(this,
                "Ngày nhập viện không hợp lệ! Định dạng: dd/MM/yyyy",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        patient.setName(txtName.getText().trim());
        patient.setBirth(txtBirth.getText().trim());
        patient.setSex(cmbSex.getSelectedIndex() == 0 ? Patient.SEX_MALE : Patient.SEX_FEMALE);
        patient.setBhyt(txtBhyt.getText().trim());
        patient.setBhytFrom(txtBhytFrom.getText().trim());
        patient.setBhytTo(txtBhytTo.getText().trim());
        patient.setNgayNhapVien(txtNgayNhapVien.getText().trim());
        
        // Combine hour and minute into "HH giờ mm phút" format
        String hour = txtGioNhapVienHour.getText().trim();
        String minute = txtGioNhapVienMinute.getText().trim();
        if (!hour.isEmpty() && !minute.isEmpty()) {
            patient.setGioNhapVien(hour + " giờ " + minute + " phút");
        } else {
            patient.setGioNhapVien("");
        }
        
        patient.setLyDoNhapVien(txtLyDoNhapVien.getText().trim());
        patient.setQuaTrinhBenhLy(txtQuaTrinhBenhLy.getText().trim());
        patient.setTienSuBenh(txtTienSuBenh.getText().trim());
        
        // Save new separate vital sign fields
        patient.setMach(txtMach.getText().trim());
        patient.setHuyetAp(txtHuyetAp.getText().trim());
        patient.setCanNang(txtCanNang.getText().trim());
        
        // Also save to vitalSigns for backward compatibility
        // Only include non-empty values
        java.util.List<String> vitalsList = new java.util.ArrayList<>();
        if (!txtMach.getText().trim().isEmpty()) {
            vitalsList.add(txtMach.getText().trim());
        }
        if (!txtHuyetAp.getText().trim().isEmpty()) {
            vitalsList.add(txtHuyetAp.getText().trim());
        }
        if (!txtCanNang.getText().trim().isEmpty()) {
            vitalsList.add(txtCanNang.getText().trim());
        }
        String vitalSigns = String.join(", ", vitalsList);
        patient.setVitalSigns(vitalSigns);
        
        patient.setChanDoanChinh(txtChanDoanChinh.getText().trim());
        patient.setChanDoanPhu(txtChanDoanPhu.getText().trim());
        patient.setBacSiDieuTri(txtBacSiDieuTri.getText().trim());
        
        if (dbManager.savePatient(patient)) {
            JOptionPane.showMessageDialog(this, 
                "Lưu thông tin bệnh nhân thành công!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lưu thông tin bệnh nhân!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addNewTreatmentRecord() {
        String ngayNhapVien = txtNgayNhapVien.getText().trim();
        
        if (ngayNhapVien.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập ngày nhập viện trước!",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateDate(ngayNhapVien)) {
            JOptionPane.showMessageDialog(this,
                "Ngày nhập viện không hợp lệ! Định dạng: dd/MM/yyyy",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Convert dd/MM/yyyy to yyyy-MM-dd for database
        String dbDate = convertToDbDate(ngayNhapVien);
        
        // Get current time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        
        // Create new treatment record
        DieuTri newDieuTri = new DieuTri(patient.getId(), dbDate, timestamp);
        
        if (dbManager.saveDieuTri(newDieuTri)) {
            JOptionPane.showMessageDialog(this,
                "Đã tạo hồ sơ điều trị mới!",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            
            // Reload patient data to get updated treatment records
            patient = dbManager.findPatientById(patient.getId());
            
            // Refresh month buttons
            monthButtonPanel.removeAll();
            createMonthButtons();
            monthButtonPanel.revalidate();
            monthButtonPanel.repaint();
            
            // Open the treatment record
            int month = Integer.parseInt(dbDate.substring(5, 7));
            openMonthRecords(month);
        } else {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tạo hồ sơ điều trị!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String convertToDbDate(String displayDate) {
        // Convert dd/MM/yyyy to yyyy-MM-dd
        String[] parts = displayDate.split("/");
        if (parts.length == 3) {
            return parts[2] + "-" + parts[1] + "-" + parts[0];
        }
        return displayDate;
    }
    
    private void openMonthRecords(int month) {
        List<DieuTri> monthRecords = new ArrayList<>();
        if (patient.getToDieuTri() != null) {
            for (DieuTri dt : patient.getToDieuTri()) {
                if (dt.getMonth() == month) {
                    monthRecords.add(dt);
                }
            }
        }
        
        Frame3 frame3 = new Frame3(dbManager, patient, month, monthRecords);
        frame3.setVisible(true);
    }
}
