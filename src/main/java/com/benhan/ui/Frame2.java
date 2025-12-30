package com.benhan.ui;

import com.benhan.database.DatabaseManager;
import com.benhan.models.Patient;
import com.benhan.models.DieuTri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Frame2 extends JFrame {
    private DatabaseManager dbManager;
    private Patient patient;
    private boolean isNewPatient;
    
    private JTextField txtId, txtName, txtBirth, txtBhyt, txtBhytFrom, txtBhytTo;
    private JTextField txtGioNhapVien, txtBacSiDieuTri;
    private JComboBox<String> cmbSex;
    private JTextArea txtLyDoNhapVien, txtQuaTrinhBenhLy, txtTienSuBenh;
    private JTextArea txtVitalSigns, txtChanDoanChinh, txtChanDoanPhu;
    private JButton btnSave, btnCancel;
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
        bhytPanel.add(txtBhytFrom);
        bhytPanel.add(new JLabel("Đến:"));
        txtBhytTo = new JTextField();
        bhytPanel.add(txtBhytTo);
        infoPanel.add(bhytPanel, gbc);
        
        row++;
        
        // Row 4: Admission time
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Giờ nhập viện:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3; gbc.gridwidth = 3;
        txtGioNhapVien = new JTextField();
        infoPanel.add(txtGioNhapVien, gbc);
        gbc.gridwidth = 1;
        
        row++;
        
        // Row 5: Admission reason
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Lý do nhập viện:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtLyDoNhapVien = new JTextArea(2, 20);
        txtLyDoNhapVien.setLineWrap(true);
        txtLyDoNhapVien.setWrapStyleWord(true);
        JScrollPane scrollLyDo = new JScrollPane(txtLyDoNhapVien);
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
        
        // Row 8: Vital signs
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Mạch, HA, CN:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtVitalSigns = new JTextArea(2, 20);
        txtVitalSigns.setLineWrap(true);
        txtVitalSigns.setWrapStyleWord(true);
        JScrollPane scrollVital = new JScrollPane(txtVitalSigns);
        infoPanel.add(scrollVital, gbc);
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
        
        // Row 11: Doctor
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        infoPanel.add(new JLabel("Bác sĩ điều trị:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1; gbc.gridwidth = 3;
        txtBacSiDieuTri = new JTextField();
        infoPanel.add(txtBacSiDieuTri, gbc);
        
        JScrollPane scrollInfo = new JScrollPane(infoPanel);
        scrollInfo.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        mainPanel.add(scrollInfo, BorderLayout.CENTER);
        
        // Button panels
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Save/Cancel buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePatient();
            }
        });
        
        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
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
    
    private void loadPatientData() {
        if (patient.getName() != null) {
            txtName.setText(patient.getName());
            txtBirth.setText(patient.getBirth() != null ? patient.getBirth() : "");
            cmbSex.setSelectedIndex(patient.getSex() == Patient.SEX_MALE ? 0 : 1);
            txtBhyt.setText(patient.getBhyt() != null ? patient.getBhyt() : "");
            txtBhytFrom.setText(patient.getBhytFrom() != null ? patient.getBhytFrom() : "");
            txtBhytTo.setText(patient.getBhytTo() != null ? patient.getBhytTo() : "");
            txtGioNhapVien.setText(patient.getGioNhapVien() != null ? patient.getGioNhapVien() : "");
            txtLyDoNhapVien.setText(patient.getLyDoNhapVien() != null ? patient.getLyDoNhapVien() : "");
            txtQuaTrinhBenhLy.setText(patient.getQuaTrinhBenhLy() != null ? patient.getQuaTrinhBenhLy() : "");
            txtTienSuBenh.setText(patient.getTienSuBenh() != null ? patient.getTienSuBenh() : "");
            txtVitalSigns.setText(patient.getVitalSigns() != null ? patient.getVitalSigns() : "");
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
                btnMonth.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openMonthRecords(month);
                    }
                });
            } else {
                btnMonth.setEnabled(false);
            }
            
            monthButtonPanel.add(btnMonth);
        }
    }
    
    private void savePatient() {
        patient.setName(txtName.getText().trim());
        patient.setBirth(txtBirth.getText().trim());
        patient.setSex(cmbSex.getSelectedIndex() == 0 ? Patient.SEX_MALE : Patient.SEX_FEMALE);
        patient.setBhyt(txtBhyt.getText().trim());
        patient.setBhytFrom(txtBhytFrom.getText().trim());
        patient.setBhytTo(txtBhytTo.getText().trim());
        patient.setGioNhapVien(txtGioNhapVien.getText().trim());
        patient.setLyDoNhapVien(txtLyDoNhapVien.getText().trim());
        patient.setQuaTrinhBenhLy(txtQuaTrinhBenhLy.getText().trim());
        patient.setTienSuBenh(txtTienSuBenh.getText().trim());
        patient.setVitalSigns(txtVitalSigns.getText().trim());
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
