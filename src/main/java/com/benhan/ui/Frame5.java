package com.benhan.ui;

import com.benhan.database.DatabaseManager;
import com.benhan.models.Patient;
import com.benhan.models.DieuTri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame5 extends JFrame {
    private DatabaseManager dbManager;
    private Patient patient;
    private DieuTri dieuTri;
    private boolean isNew;
    
    private JTextArea txtDienTien;
    private JTextArea txtYLenh;
    private JComboBox<String> cmbChanDoan;
    private JTextField txtBacSiDieuTri;
    private JButton btnSave;
    private JButton btnCancel;
    
    public Frame5(DatabaseManager dbManager, Patient patient, DieuTri dieuTri, boolean isNew) {
        this.dbManager = dbManager;
        this.patient = patient;
        this.dieuTri = dieuTri;
        this.isNew = isNew;
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setTitle("CHI TIẾT ĐIỀU TRỊ");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Info label
        JLabel lblInfo = new JLabel("Bệnh nhân: " + patient.getName() + " - Thời gian: " + dieuTri.getTimestamp());
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblInfo, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Chẩn đoán
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.weighty = 0;
        JLabel lblChanDoan = new JLabel("Chẩn đoán:");
        lblChanDoan.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblChanDoan, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1; gbc.weighty = 0;
        cmbChanDoan = new JComboBox<>();
        cmbChanDoan.setEditable(true);
        cmbChanDoan.setFont(new Font("Arial", Font.PLAIN, 13));
        populateDiagnosisComboBox();
        contentPanel.add(cmbChanDoan, gbc);
        
        // Bác sĩ điều trị
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.weighty = 0;
        JLabel lblBacSi = new JLabel("Bác sĩ điều trị:");
        lblBacSi.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblBacSi, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 1; gbc.weighty = 0;
        txtBacSiDieuTri = new JTextField();
        txtBacSiDieuTri.setFont(new Font("Arial", Font.PLAIN, 13));
        contentPanel.add(txtBacSiDieuTri, gbc);
        
        // Diễn tiến
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; gbc.weighty = 0;
        JLabel lblDienTien = new JLabel("Diễn tiến:");
        lblDienTien.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblDienTien, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 1; gbc.weighty = 0.5;
        txtDienTien = new JTextArea(8, 40);
        txtDienTien.setLineWrap(true);
        txtDienTien.setWrapStyleWord(true);
        txtDienTien.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollDienTien = new JScrollPane(txtDienTien);
        scrollDienTien.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.add(scrollDienTien, gbc);
        
        // Y lệnh
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0; gbc.weighty = 0;
        JLabel lblYLenh = new JLabel("Y lệnh:");
        lblYLenh.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblYLenh, gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.weightx = 1; gbc.weighty = 0.5;
        txtYLenh = new JTextArea(8, 40);
        txtYLenh.setLineWrap(true);
        txtYLenh.setWrapStyleWord(true);
        txtYLenh.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollYLenh = new JScrollPane(txtYLenh);
        scrollYLenh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.add(scrollYLenh, gbc);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        btnSave = new JButton("Lưu");
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setPreferredSize(new Dimension(100, 35));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTreatment();
            }
        });
        
        btnCancel = new JButton("Hủy bỏ");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setPreferredSize(new Dimension(100, 35));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadData() {
        if (!isNew && dieuTri.getDienTien() != null) {
            txtDienTien.setText(dieuTri.getDienTien());
        }
        if (!isNew && dieuTri.getYLenh() != null) {
            txtYLenh.setText(dieuTri.getYLenh());
        }
        if (!isNew && dieuTri.getChanDoan() != null) {
            cmbChanDoan.setSelectedItem(dieuTri.getChanDoan());
        }
        if (!isNew && dieuTri.getBacSiDieuTri() != null) {
            txtBacSiDieuTri.setText(dieuTri.getBacSiDieuTri());
        } else {
            // Default to patient's doctor if available
            if (patient.getBacSiDieuTri() != null && !patient.getBacSiDieuTri().isEmpty()) {
                txtBacSiDieuTri.setText(patient.getBacSiDieuTri());
            }
        }
    }
    
    private void populateDiagnosisComboBox() {
        // Add empty option
        cmbChanDoan.addItem("");
        
        // Add patient's main diagnosis
        if (patient.getChanDoanChinh() != null && !patient.getChanDoanChinh().trim().isEmpty()) {
            String mainDiag = patient.getChanDoanChinh().trim();
            if (!mainDiag.isEmpty()) {
                cmbChanDoan.addItem(mainDiag);
            }
        }
        
        // Add patient's additional diagnosis
        if (patient.getChanDoanPhu() != null && !patient.getChanDoanPhu().trim().isEmpty()) {
            String addDiag = patient.getChanDoanPhu().trim();
            if (!addDiag.isEmpty()) {
                cmbChanDoan.addItem(addDiag);
            }
        }
        
        // Add combined diagnosis if both exist
        if (patient.getChanDoanChinh() != null && !patient.getChanDoanChinh().trim().isEmpty() &&
            patient.getChanDoanPhu() != null && !patient.getChanDoanPhu().trim().isEmpty()) {
            String combined = patient.getChanDoanChinh().trim() + " + " + patient.getChanDoanPhu().trim();
            cmbChanDoan.addItem(combined);
        }
        
        // Add previous diagnoses from treatment history
        if (patient.getToDieuTri() != null) {
            java.util.Set<String> uniqueDiagnoses = new java.util.HashSet<>();
            for (DieuTri dt : patient.getToDieuTri()) {
                if (dt.getChanDoan() != null && !dt.getChanDoan().trim().isEmpty()) {
                    String diag = dt.getChanDoan().trim();
                    if (!uniqueDiagnoses.contains(diag)) {
                        uniqueDiagnoses.add(diag);
                        // Only add if not already in combo box
                        boolean exists = false;
                        for (int i = 0; i < cmbChanDoan.getItemCount(); i++) {
                            if (cmbChanDoan.getItemAt(i).equals(diag)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            cmbChanDoan.addItem(diag);
                        }
                    }
                }
            }
        }
    }
    
    private void saveTreatment() {
        dieuTri.setDienTien(txtDienTien.getText().trim());
        dieuTri.setYLenh(txtYLenh.getText().trim());
        
        // Get diagnosis from combo box (supports both selection and manual entry)
        Object selectedItem = cmbChanDoan.getSelectedItem();
        String chanDoan = selectedItem != null ? selectedItem.toString().trim() : "";
        dieuTri.setChanDoan(chanDoan);
        
        dieuTri.setBacSiDieuTri(txtBacSiDieuTri.getText().trim());
        
        if (dbManager.saveDieuTri(dieuTri)) {
            JOptionPane.showMessageDialog(this, 
                "Lưu thông tin điều trị thành công!", 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lưu thông tin điều trị!", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
