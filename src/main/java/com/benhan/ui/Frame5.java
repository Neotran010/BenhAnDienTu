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
        
        // Diễn tiến
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.weighty = 0;
        JLabel lblDienTien = new JLabel("Diễn tiến:");
        lblDienTien.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblDienTien, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1; gbc.weighty = 0.5;
        txtDienTien = new JTextArea(8, 40);
        txtDienTien.setLineWrap(true);
        txtDienTien.setWrapStyleWord(true);
        txtDienTien.setFont(new Font("Arial", Font.PLAIN, 13));
        JScrollPane scrollDienTien = new JScrollPane(txtDienTien);
        scrollDienTien.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.add(scrollDienTien, gbc);
        
        // Y lệnh
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.weighty = 0;
        JLabel lblYLenh = new JLabel("Y lệnh:");
        lblYLenh.setFont(new Font("Arial", Font.BOLD, 14));
        contentPanel.add(lblYLenh, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 1; gbc.weighty = 0.5;
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
    }
    
    private void saveTreatment() {
        dieuTri.setDienTien(txtDienTien.getText().trim());
        dieuTri.setYLenh(txtYLenh.getText().trim());
        
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
